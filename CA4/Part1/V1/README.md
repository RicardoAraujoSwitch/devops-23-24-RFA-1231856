Ricardo Araújo 1231856

# Class Assignment 4 Part 1

## Introduction

The aim of this assignment was to use Docker to create an image and run a container using that image. For this task, two different Dockerfiles were created, each representing a different version of the same application.

## Version 1

For this version, the requirement was to create a Dockerfile to build and run an already implemented project. Clone the repository containing the project from CA2 Part 1, build it, and run the application. You may need to modify the permissions of `gradlew` to execute it. The chat server will be launched on port 8080. Here is the Dockerfile for Version 1:

```bash
# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Install dependencies
RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless git dos2unix && \
    apt-get clean

# Set the working directory to /app
WORKDIR /app

# Copy contents to /app directory in the container
COPY . /app

# Modify gradlew permissions and convert to Unix format
RUN chmod +x ./gradlew && dos2unix gradlew
# Build the application
RUN ./gradlew build

# Expose port 8080 for the chat server
EXPOSE 8080

# Run the chat server
CMD ["./gradlew", "build" "runServer"]
```

### Running the Server
1. To create the image, navigate to the directory containing the Dockerfile and run:
```bash
docker build -t chatServer.
```
* The `-t` flag tags the image with a name. Ensure to include the dot at the end to specify the Dockerfile's location.

2. To start the container, run:
```bash
docker run -p 8080:8080 chatServer
```
3. To start the chat client, use:
```bash
./gradlew runClient
```

4. To push the image to Docker Hub, use the following commands:
```bash
docker tag chatServer <your_dockerhub_username>/chatServer 
```
```bash
docker push <your_dockerhub_username>/chatServer 
```

## Version 2

In this version, the project files are copied directly from the local machine, built, and the server is run. Follow these steps to prepare the Dockerfile and run the application:

1. Navigate to the `CA4/Part1/V2` directory and copy the project files:
```bash
cp -r ../../../CA2/Part1/gradle_basic_demo .
```

2. Now create the Dockerfile:

```dockerfile
# Use openjdk:17-jdk-slim as the base image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy contents to /app directory in the container
COPY . /app

# Expose port 8080 for the chat server
EXPOSE 8080

# Run the chat server with the specified port
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "8080"]
```

### Running the Server

1. To build the image, navigate to the directory containing the Dockerfile and run:
```bash
docker build -t ChatServer.
```
2. To start the container, run:
```bash
docker run -p 8080:8080 ChatServer
```

## Conclusion

The main difference between the two versions lies in the build process. Version 1 builds the project inside the Docker container using Gradle, which includes copying the source code, installing dependencies, and executing build commands. In contrast, Version 2 assumes the project is already built and only copies the pre-built JAR file into the container, running it directly without additional build steps. This makes Version 2 faster and simpler, avoiding the build process inside the container.

Repository URL: [https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856]
