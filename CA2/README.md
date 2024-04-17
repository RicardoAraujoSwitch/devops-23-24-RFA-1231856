# Technical Report for Class Assignment 2 - Part1
by Ricardo Araujo 1231856

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

## Part 2

The second part of the assignment revolves around a GradleSpring Boot project with the inclusion of frontend. We will need to firstly add the necessary dependencies, import the required front end pluging, and create the tasks for the copy of the generated JAR file that will in turn delete webpack files.
This assignment will be developed via the creation of a new branch (Tut-basic-gradle) which will then be merged into the master branch. As usual, you should create issues for the execution of each task in this part.

# Introduction

1- Branch creation:

```bash
git branch tut-basic-gradle
```
And then switch to that branch:

```bash
git checkout tut-basic-gradle
```
2 - Creation of a new gradle spring Project can be done by accessing [https://start.spring.io], filling the fields and picking the following required dependencies:

- Rest Repositories;
- Thymeleaf;
- Spring Data JPA;
- H2 Database;

3- Afterwards, generate the project and extract the corresponding files to the CA2/Part 2 folder.

4- As we will use the same classes as in the Class Assignment 1, we can delete the source (src) in the downloaded folder, and copy the former to the Part 2 directory:

```bash
cp -r DevOpsRep/CA1/part1/src DevOpsRep/CA2/part2
```
5- We will also copy files such as a webpack.config.js and the package.json from Class Assignment 1 to the project folder:

```bash
cp DevOps/CA1/part1/webpack.config.js DevOps/CA2/part2
```
6- Finally, delete built folder in the path
**src/main/resources/static/built/**

```bash
rm -r src/main/resources/static/built/
```

7- Go to the Employee.java class, in the source folder of the Class Assignment 2 and switch the persistence plugin instances from *javax.persistence* to *jakarta.persistence*.

8- Now add and commit the relevant files:

```bash
git add .
```

```bash
git commit -m "[Feat] Close #12 Create branch "tut-basic-gradle", added required dependencies and files for gradle execution.
```

9- Now push the changes to your repository:

```bash
git push
```
## Implementation of changes

## Addition of Frontend Plugin:

In order to add frontend funcionalities to our project, we will need to add the required frontend plugin.

10- In the build configuration file (build.gradle) add the pluging to the corresponding java version you are using:

```gradle
 id "org.siouan.frontend-jdk17" version "8.0.0"
```
11- We will also add the frontend block configuration by adding the following to the file:

```gradle
 frontend {
nodeVersion = "16.20.2"
assembleScript = "run build"
cleanScript = "run clean"
checkScript = "run check"
}
```

12- Now update the scripts for the configuration of the execution of the webpack:

```gradle
"scripts": {
"webpack": "webpack",
"build": "npm run webpack",
"check": "echo Checking frontend",
"clean": "echo Cleaning frontend",
"lint": "echo Linting frontend",
"test": "echo Testing frontend"
},
```

13- Just above the scripts section in the same file, add the respective packageManager:

```gradle
"packageManager": "npm@9.6.7",
```
14- Finally, compile the project by running the following command in the project folder:

```bash
./gradlew build
```

15- Lastly, add the changes to the staging area, commit them and push:

```bash
git add .
```

```bash
git commit -m "[Feat] Close #13 Add a gradle plugin for frontend code generation and execution in project."
```

```bash
git push
```

## Part 2: Addition of  the copyJar (file) task

16- In the build.gradle add the following task:

```gradle
task copyJar(type: Copy) {
	dependsOn build

	from "$buildDir/libs"
	into "$projectDir/dist"

	include "*.jar"
}
```

17- Compile the project:

```bash
./gradlew build
```

18- And add, commit and push the changes:

```bash
git add .
```

```bash
git commit -m "[Feat] Close #14 Add a task to gradle to copy the generated jar."
```

```bash
git push
```

## Part 3: Addition of a task for the deletion of WebPackFiles

19- In the build.gradle file, add a new task with the following code:

```gradle
task deleteGeneratedFiles(type: Delete) {
	delete fileTree(dir: 'src/main/resources/static/built', include: '**/')
}
```

20- To make sure that the above command is automatically executed in the clean task, add this after the task code:

```gradle
clean.dependsOn(deleteGeneratedFiles)
```

21- Once again, compile, add commit and push the changes:

```bash
./gradlew build
```
```bash
git add .
```

```bash
git commit -m  "[Feat] Close #15 Add a task to gradle to delete all the files generated by webpack"
```

```bash
git push
```

## Part 4: Branch merging

22- Switch to your master branch:

```bash
git checkout master
```

23- Merge the branch used for this assignment into the master branch, and push the changes:

```bash
git merge --no-ff tut-basic-gradle
```

```bash
git push
```

24- Add a tag to mark the end of this second part and push it:

```bash
git tag ca2-part2
git push --tags
```

## Maven Alternative

# Maven was chosen as an alternative solution to Gradle.
It differs in its configuration setup by using a pom.xml (as opposed to a Groovy-based DSL used by Gradle) file for project structure, dependencies, and plugins. It also contains a central repository system that is used to automatically download project dependencies. They are listed in the pom.xml file with specifications regarding their group ID, artifact ID and version.


## Maven Setup

1- We will start by creating a folder for the alternative implementation (e.g. Part2Maven). In it, we will add the necessary files for the configuration and execution of the assignment using the alternative.

```bash
mkdir DevOpsRep/CA2/Part2maven
```

2- Copy the src folder, .mvn files (minus the maven-wrapper.jar), mvnw, mvnw.cmd, the webpack.config.js and package.json files, and the pom.xml file from the basic module of CA1 to CA2/Part2Maven.

3- In the pom.xml file, make sure you add the following plugins:

3.1- To copy the generated JAR file, which will be used for the task copyJarFile, add the following pluging code:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-resources-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>copy-resources</id>
            <phase>install</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.basedir}/dist</outputDirectory>
                <resources>
                    <resource>
                        <directory>${project.build.directory}</directory>
                        <includes>
                            <include>*.jar</include>
                        </includes>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

3.2- For the deletion of files in the /static/built directory for the removal of the webpack-generated files, add the following plugin:

```xml
<plugin>
    <artifactId>maven-clean-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <filesets>
            <fileset>
                <directory>${project.basedir}/src/main/resources/static/built</directory>
                <includes>
                    <include>**/*</include>
                </includes>
            </fileset>
        </filesets>
    </configuration>
</plugin>
```
## Execution of the tasks (via plugins)

4- After the setup of the configuration file (pom.xml), we will execute the compilation and installation of the project. For compilation:

```bash
mvn compile
```

And for install:

```bash
mvn install
```

5- We will the following command for the copy of the JAR file (so it allows for the execution of the above mentioned plugin). Do notice that the file will be copied to the directory specified in the outputdirectory of the plugin.

```bash
mvn resources:copy-resources
```

6- And for the execution of the clean plugin:

```bash
mvn clean
```

7- Finally, run the Spring Boot application:

```bash
mvn spring-boot:run
``` 

