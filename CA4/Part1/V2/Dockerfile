FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . /app

EXPOSE 8080
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp","8080"]
