# CA3 - Ricardo Araujo

 Part 1 - Virtualization using Vagrant

- ## Creation of a Virtual Machine

1)  Download and Install VirtualBox or UTM: Start by downloading VirtualBox from [Oracle's website](https://www.virtualbox.org/).

2) Download the Ubuntu Server ISO file from [Ubuntu's official site](https://ubuntu.com/download/server).
3) Open VirtualBox/UTM and click on "New" to create a new virtual machine.
4) Name your VM and select Ubuntu Server ISO file.

5) Select a username and password for your VM and check the box to install Guest Additions.

6) Allocate memory and processor cores to your VM.

7) Create a virtual hard disk for your VM.

8) Start the VM and follow the on-screen instructions to install Ubuntu. During installation, select the standard utilities for a server and, if prompted.

- ## Installation of the project's dependencies

A) Start by installing git:
```bash
sudo apt install git
```
B) Now procceed to install Maven
```bash
sudo apt install maven
```
C) Now gradle
```bash
sudo apt install gradle
```
D) Now perform the java installation (version 17 was installed in order for the CA2 Part2 to be able to run).

```bash
sudo apt install openjdk-17-jdk openjdk-17-jre
```

E) Confirm the java version before proceeding
```bash
java -version
```
F) You can also change the current version if the current version is invalid:
```bash
sudo update-alternatives --config java
```

- ## Cloning the repository

- Start by creating the folder and cloning the repository (in this case, see my example):

```bash
mkdir ~/DevOps
git clone <https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git>
```

- ## Configuration of gradle and maven wrapper

A) Start by going to the project root and give permissions for maven and gradle execution:
```bash
chmod +x mvnw
chmod +x gradlew
```
- ## Executing Class Assignment 1 (CA1)

A) Run the project in the Virtual Machine:

```bash
cd CA1/basic
./mvnw install
./mvnw spring-boot:run
```