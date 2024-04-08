# Technical Report for Class Assignment 2 - Part1

## Introduction

This ReadMe details the technical report for the Class Assignment 2 - Part 1

Throughout this report we will encounter the necessary steps for the completion of the first part of the Class assignment 2, presented in sequence and, whenever necessary, with a brief explanation of each one, followed by the commands used, potential issues that might arise, and the proposed solutions for such scenarios.

## 1. Tutorial

0. The creation of Issues should precede any further implementations. The issues will consist on:

- Initiate CA2 Part 1 by cloning repository;
- Add a new task to execute the server;
- Add a simple unit test and update the gradle script;
- Add a new task of type Copy to be used to make a backup of the sources of the application;
- Add a new task of type Zip to be used to make an archive;

1. Firstly, the directory for CA2.Part1 should be created on the existing Repository (DevOps)

```bash
mkdir CA2.Part1
```

2. Download the necessary Gradle demo by cloning via link https://bitbucket.org/pssmatos/gradle_basic_demo/, and add it to the repository (Part1).
   As usual, it's important not to forget to remove the .git directory of the existing cloned demo.

```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo/


3. Before the implementation of the next steps, it would be important to read the instructions given in the respective readme.file to become more confortable with the application. An example can be the creation of a simple .jar file, with the following command:

```bash
./gradlew build
```

This command will compile and run the project, running tests and summarily generate the necessary files to run it.

4. Add a new task to the server. Make sure you are in the project's root directory and use the terminal (you can use the integrated terminal in the IDE). In regard to the configuration of the chat server, make sure you insert a valid port number (ex.: 8080).

Add these tasks in the build.gradle file:

```bash
task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server that listens on port 8080"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'
}


Now for the client:


```bash
task runClient(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat client that connects to a server on localhost:8080 "

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatClientApp'

    args 'localhost', '8080'
}
```

Now build and run:

```bash
java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp <server port>
```

```bash
./gradlew runServer
```

```bash
./gradlew runClient
```

These two should be done in two different terminals, with the goal of having a chatCliente and Server that communicate with each other (with their own name), assuming the port is correct and the the server was initiated server.

5. Now we will add a simple unit test and execute it.

```java
public class AppTest {
    @Test public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("The app should have a greeting", classUnderTest.getGreeting());
    }
}
```

Run the test:

```bash
./gradlew test
```

6. Add a new task (type: Copy) in order to to produce a backup of the sourcesof the application. It should make a copy of the contents located in the src folder to a new backup one. Once again, this task should be added to the build.gradle file.

```bash
task copySources(type: Copy) {
    group = "DevOps"
    description = "Copies the source files to the specified directory"
    from 'src'
    into 'backup'
}
```

After this is done, you can backup the contents using this command:

```bash
./gradlew copySources
```

7. Add a new task of type Zip to be used to make an archive (i.e., zip file) of the sources of the application. It should copy the contents of the src folder to a new zip file, that will be created in the backup directory

```bash
task zipSources(type: Zip) {
    group = "DevOps"
    description = "Zip source files to the specified directory"

    from 'src'
    archiveFileName = 'src.zip'
	    getArchiveFile().set(layout.buildDirectory.file('backup/source.zip'))
}
```

Afterwards, you can created the Zip with the command:

```bash
./gradlew zipSources
```

8. At the end of the part 1 of this assignment mark your repository with the tag ca2-part1.

```bash
git tag ca2-part1
git push origin ca2-part1
```