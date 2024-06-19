# Class Assignment 5 Ricardo Araújo 1231856

## Introduction

The goal of this class assignment was to use create a pipeline with Jenkins that builds and pushes the images to Docker.
Before that, a similar, simpler pipeline was created for practice. Both pipelines had similar tasks like Checkout,
Assemble, Test and Build but the second one also pushed the Docker image to Dockerhub.

### Initial Steps

1. Start by downloading the latest Docker version from the official website (https://www.jenkins.io/download/).

2. Proceed to the directory where the Jenkins.war file is located and run the following command:

`java -jar jenkins.war --httpPort=[designated port number].

3. You can now go to the browser and access to access Jenkins.

`localhost:[port number]`


4. It's necessary to install the necessary plugins. Move to the Jenkins Dashboard > Manage Jenkins > Manage Plugins, and install the ones below:

    - HTML Publisher
    - Docker Pipeline
    - Docker Commons Plugin
    - Docker Plugin
    - Docker API Plugin

## Jenkins File Part1 (Practice):

```
pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: "${REPO_URL}", branch: 'master'
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2/Part1') {
                    echo 'Assembling the application...'
                    bat 'gradlew.bat assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2/Part1') {
                    echo 'Running unit tests...'
                    bat 'gradlew.bat test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA2/Part1') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
```

This pipeline automates: the build, testing, and archiving process for a Java application using Gradle.
Stages:
- Checkout: retrieves the code from the specified Git repository;
- Assemble: responsible for compiling the application;
- Test: running the unit tests and publishing the results;
- Archive: archiving the compiled .jar files.

Part 2

In this part, we're gonna produce another pipeline with the inclusion of stages with setup for the executable permissions on the Gradle wrapper, generation and publish of Javadocks, creation of a dockerfile, and building and publishing a docker image.

The following docker file contains the upload of the Docker image to the DockerHub. To complete this step, we will need to include the Dockerhub credentials in Jenkins. During the setup of the pipeline, we can add credentials by entering our DockerHub username and password.
It's necessary to generate a token in a DockerHub and its respective ID as CREDENTIALS_ID. If you have an error visible while running the build, verify if it pertains to a failure of login. To verify and/or confirm your credentials, you can click on Jenkins dashboard > manage jenkins > manage credentials.

JenkinsFile

```
pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'd01ab03c-356d-4092-8288-172086f48334'
        DOCKER_IMAGE = 'zoth00/springbootapp'
        DOCKER_TAG = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: 'https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git'
            }
        }

        stage('Set Permissions') {
            steps {
                dir('CA2/Part2/') {
                    echo 'Setting executable permissions on gradlew...'
                    bat 'gradlew.bat'
                }
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2/Part2/') {
                    echo 'Assembling the application...'
                    bat './gradlew.bat assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2/Part2/') {
                    echo 'Running unit tests...'
                    bat './gradlew.bat test'
                }
            }
        }

        stage('Javadoc') {
            steps {
                dir('CA2/Part2/') {
                    echo 'Generating Javadoc...'
                    bat './gradlew.bat javadoc'
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc'
                    ])
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA2/Part2/') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Create Dockerfile') {
                    steps {
                        dir('CA2/Part2/') {
                            script {
                                def dockerfileContent = """
                                FROM tomcat:10
                                RUN apt-get update && apt-get install -y dos2unix
                                WORKDIR /app
                                COPY . .
                                EXPOSE 8080
                                RUN dos2unix ./gradlew
                                ENTRYPOINT ["./gradlew"]
                                CMD ["bootRun"]
                                """
                                writeFile file: 'Dockerfile', text: dockerfileContent
                            }
                        }
                    }
                }

        stage('Publish Image') {
            steps {
                script {
                    echo 'Building and publishing Docker image...'
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                        dir('CA2/Part2/') {
                            def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                            customImage.push('latest')
                        }
                    }
                }
            }
        }
    }
}

```

## Conclusion


This report explains how to set up Jenkins pipelines for automating critical stages of a development project. 
These stages encompass code checkout, application assembly, testing, artifact archiving, and Docker image publishing.
Initially, a basic pipeline for practice was created. It executed basic tasks such as retrieving code, compiling the application, running unit tests, and archiving build artifacts.
After the practice task, a more complex pipeline was developed. This enhanced version included more steps such as setting permissions for the Gradle wrapper, generation and publishing of the Javadocs, creation of a Dockerfile, and build/publish a Docker image to DockerHub.
A key aspect of the setup was configuring DockerHub credentials in Jenkins. This involved generating a token in DockerHub and using it as the credentials ID in Jenkins, which is essential for pushing Docker images successfully.
The report underscores the importance of using the correct commands and environment variables specific to the operating system hosting Jenkins. It also provides troubleshooting advice and solutions for common errors (such as issues with DockerHub credentials).
Setting up these Jenkins pipelines is a significant step forward in automating and optimizing the development workflow.