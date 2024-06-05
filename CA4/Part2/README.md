Ricardo Araújo 1231856

CA4 Part 2

# Class Assignment 4 Part 2

## Introduction

The goal of this class assignment was to use Docker to create two images and a docker-compose file, and run containers with those images. Specifically, one image runs a web application and the other hosts an H2 database.

## Table of Contents

1. [Preparing files](#preparing-files)
2. [Web Dockerfile](#web-dockerfile)
3. [Database Dockerfile](#database-dockerfile)
4. [Working with Docker-compose](#docker-compose-file)
5. [Alternative Solution](#alternative-solution)

## Getting Started

Begin by copying the contents of the CA2 Part2 project to the same location of your dockerfiles, in order for them to copy the contents of the project and correctly run the server.

## Web Dockerfile

The Web Dockerfile is responsible for creating an image that runs the web application. This Dockerfile uses a Gradle base image to build and run the project. It copies the project contents, makes the `gradlew` file executable, and exposes the necessary port. Below is the Dockerfile:

```bash
FROM tomcat
RUN apt-get update && apt-get install -y dos2unix
WORKDIR /app

COPY . .

EXPOSE 8080

RUN dos2unix ./gradlew

ENTRYPOINT ["./gradlew"]
CMD ["bootRun"]
```

To push this image to Docker Hub, use the following commands:

```bash
docker tag <local_image>:<tag> <dockerhub_username>/<repository>:<tag>
```

```bash
docker push <dockerhub_username>/<repository>:<tag>
```

## Database Dockerfile

The Database Dockerfile creates an imagem which runs the H2 database. It uses an Ubuntu base image, installs necessary dependencies, and downloads the H2 database JAR file. It then runs the H2 database server. Below is the Dockerfile:

```bash
FROM ubuntu

RUN apt-get update && \
apt-get install -y openjdk-17-jdk-headless && \
apt-get install unzip -y && \
apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

CMD java -cp /opt/h2.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

If you want to push the image to Docker Hub, use the following commands:

```bash
docker tag <local_image>:<tag> <dockerhub_username>/<repository>:<tag>
```

```bash
docker push <dockerhub_username>/<repository>:<tag>
```

## Working with Docker-compose

The Docker-compose file is responsible for orchestrating the two images and running the containers. It defines the services for both the web application and the H2 database, sets up networking and volume configurations, and specifies environment variables required for the web application to connect to the database. Below is the Docker-compose file:

```bash
version: '3.8'

services:
db:
build:
context: .
dockerfile: Dockerfile.db
container_name: db
ports:
- "8082:8082"
- "9092:9092"
volumes:
- h2-data:/app/db-backup

web:
build:
context: .
dockerfile: Dockerfile.web
container_name: web
ports:
- "8080:8080"
depends_on:
- db
environment:
SPRING_DATASOURCE_URL: jdbc:h2:tcp://db:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

volumes:
h2-data:
driver: local
```

After starting the containers, the web app can be accessed through the IP specified in the Docker-compose file (typically localhost) on port 8080, while the H2 database console can be accessed on port 8082.


## Alternative Solution

An alternative solution for this assignment is to deploy the app and the database in a Kubernetes cluster. This setup enhances scalability and availability. Each component (app and database) is deployed in separate pods, with the app connecting to the database via its service name.

After pushing the Docker images to Docker Hub, create Kubernetes deployment and service files for the app and the database. Below are examples of these files:

### Web App Deployment File
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: CA4-Part2-web
spec:
  replicas: 1
  selector:
    matchLabels:
      app: CA4-Part2-web
  template:
    metadata:
      labels:
        app: CA4-Part2-web
    spec:
      containers:
        - name: CA4-Part2-web
          image: <dockerhub_username>/devops_23_24:CA4_Part2_web
          ports:
            - containerPort: 8080
```

### Web App Service File
```bash
apiVersion: v1
kind: Service
metadata:
  name: CA4-Part2-web
spec:
  type: NodePort
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 30080
  selector:
    app: ca4-part2-web
```

### Database Deployment File
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: CA4-Part2-db
spec:
  replicas: 1
  selector:
    matchLabels:
      app: CA4-Part2-db
  template:
    metadata:
      labels:
        app: CA4-Part2-db
    spec:
      containers:
        - name: CA4-Part2-db
          image: <dockerhub_username>/devops-23-24-RFA-1231856:CA4_Part2_db
          ports:
            - containerPort: 8082
            - containerPort: 9092
```

### Database Service File
```bash
apiVersion: v1
kind: Service
metadata:
  name: CA4-Part2-db
spec:
  type: NodePort
  ports:
    - port: 8082
      targetPort: 8082
      nodePort: 30082
    - port: 9092
      targetPort: 9092
      nodePort: 30092
  selector:
    app: CA4-Part2-db
```

The use of Kubernetes provides a declarative approach to manage the desired state of the cluster via YAML files, ensuring better management, scalability, and availability. Docker and Kubernetes complement each other; Docker builds the images, and Kubernetes deploys and manages the containers, improving separation of concerns and management efficiency.
