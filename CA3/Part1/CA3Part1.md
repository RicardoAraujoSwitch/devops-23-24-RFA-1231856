
# CA3 - Ricardo Araujo - 1231856@isep.ipp.pt

## Part 1 - Setup of Virtual Box and implementation of Virtual Machine

### Creation of a Virtual Machine

1. Download and Install VirtualBox or UTM: Start by downloading VirtualBox from [Oracle's website](https://www.virtualbox.org/).

2. Download the Ubuntu Server ISO file from [Ubuntu's official site](https://ubuntu.com/download/server).

3. Open VirtualBox/UTM and click on "New" to create a new virtual machine.

4. Give a name to your virtual machine and select the Ubuntu Server ISO file.

5. Select a username/password for your VM and make sure you signal the box in order to install the Guest Additions.

6. Allocate the necessary memory and number of processor cores usage to your VM.

7. Create a virtual hard disk for your VM.

8. Start the VM and proceed to install Ubuntu. If prompted during installation, select the standard utilities for a server.

### Installation of the project's dependencies

A) Begin by installing git:
```bash
sudo apt install git
```
B) Install Maven
```bash
sudo apt install maven
```
C) Now gradle
```bash
sudo apt install gradle
```
D) Now perform the Java installation (version 17 was installed in order for the CA2 Part2 to be able to run).
```bash
sudo apt install openjdk-17-jdk openjdk-17-jre
```
E) Verify the Java Version:
```bash
java -version
```
F) You can also change the current version if the current version is invalid:
```bash
sudo update-alternatives --config java
```

### Cloning the repository

Start by creating the folder and cloning the repository (in this case, see my example):
```bash
mkdir ~/DevOps
git clone https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git
```

### Configuration of gradle and maven wrapper

A) Start by going to the project root and give permissions for maven and gradle execution:
```bash
chmod +x mvnw
chmod +x gradlew
```

### Executing Class Assignment 1 (CA1)

A) Start by running the project in the Virtual Machine:
```bash
cd CA1/basic
./mvnw install
./mvnw spring-boot:run
```

Use the following command to obtain the IP address of the VM and the port used by the project to access the application from the web browser:
```bash
ip addr
```

### Run Class Assignment 2 part 1

A) Start by running the server in the VM:
```bash
cd CA2/part1
./gradlew build
./gradlew runServer
```

B) Now run the clients in the host machine. Don't forget to once again use the IP address of the VM and the port used by the project to access the application from the web browser:
```bash
cd CA2/part1
./gradlew build
./gradlew runClient --args="<ip> <port>"
```

### Running Class Assignment 2 part 2

A) Run the project inside the VM:
```bash
cd CA2/part2
./gradlew build
./gradlew bootRun
```

As you did for Part 1, don't forget to once again use the IP address of the VM and the port used by the project to access the application from the web browser. You'd test in your browser like this: (http://VM-ip:port-number/)
```bash
ip addr
```

## Conclusion

We've concluded the set up of the virtualized development environment with VirtualBox.
