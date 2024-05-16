
# CA3 - Ricardo Araujo

## Part 2 - Setup of a Virtual Environment using Vagrant

### Introduction
The goal of this part of the assignment is to use Vagrant to setup a virtual environment to execute the tutorial spring boot application, gradle ”basic” version (developed in CA2, Part2).

1. Guarantee that you have installed both Vagrant and the VirtualBox beforehand. You can download/install vagrant via the official website, and confirm the successful installation via the command:
```bash
vagrant -v
```

2. Create a Vagrant Project:
```bash
mkdir my-vagrant-project
```

### Configurating Vagrant

Vagrant configuration is performed at the level of the Vagrant file, where you can establish settings such as version and virtual machine settings.

3. Open the Vagrantfile (you can use a text editor) and replace its content with the configuration for the two virtual machines: a database and a webserver one, which will both be running Ubuntu Focal 64-bit.
- *config.vm.box = "ubuntu/focal64":* This line sets the base image for the VMs to Ubuntu 20.04 (Focal Fossa).
- *config.ssh.insert_key = false:* This line disables automatic key insertion, meaning Vagrant won't replace the default insecure key pair on the first vagrant up.
- *config.vm.provision "shell", inline: <<-SHELL ... SHELL:* This block is a shell provisioner that runs shell commands on the VMs during provisioning. The commands update the package lists, install necessary packages, and download the H2 database jar file.
- *config.vm.define "db" do |db| ... end:* This block defines a VM named "db". It sets the VM's hostname to "db", configures a private network with a static IP, sets up port forwarding for the H2 console and server, and provisions the VM to run the H2 server.
- *config.vm.define "web" do |web| ... end:* This block defines a VM named "web". It sets the VM's hostname to "web", configures a private network with a static IP, sets the VM's memory to 1024 MB, sets up port forwarding for Tomcat, and provisions the VM to install Tomcat, clone a Git repository, build a project with Gradle, and run the project.

4. Initialize the Vagrant Boxes with the command "vagrant up". It will start by downloading Ubuntu Focal 64-bit box (if it isn't present) and start the virtual machines based on the configuration file.
```bash
vagrant up
```

If at some point you run into an error, you can use the following commands:
- **\`vagrant halt\`**: to stop your currently running Virtual Machines;
- **\`vagrant destroy\`**: Stops and deletes/removes the Virtual Machine(s) that were defined in the VagrantFile.
- **\`vagrant reload\`**: Restarts the Virtual Machines and re-provisions them, applying any changes in the \`Vagrantfile\`.

5. Using SSH in the VM
- You can SSH into the VMs using the following command:
```bash
vagrant ssh
```

6. Clone your repository
- Clone your existing project using git or any other version control tool.
```bash
git clone https://github.com/RicardoAraujoSwitch/devops-23-24-RFA-1231856.git
```

7. Build and run your project using the above mentioned commands:
- Permissions via chmod;
- Build via clean build;
- Run via bootRun;

### Conclusion
We've concluded the set up of the virtualized development environment with Vagrant.

## Part 2 Alternative - Hyper V

### Introduction
Hyper-V and VirtualBox are both popular virtualization solutions but cater to different needs and environments.
Hyper-V, a Windows-specific hypervisor, offers superior performance, dynamic memory allocation, and advanced networking features, making it ideal for enterprise use on Windows systems. However, it lacks cross-platform support, limiting its use to Windows environments. VirtualBox, on the other hand, provides cross-platform compatibility, making it accessible on Windows, macOS, Linux, and Solaris.
It is user-friendly with a graphical interface, but tends to be more resource-intensive and slower compared to Hyper-V.
VirtualBox is suitable for development environments that require a full OS and operate across various platforms.

Hyper-V can be used with Vagrant to achieve the same goals as VirtualBox for this assignment. 
By configuring the Vagrantfile to use Hyper-V as the provider, you can can create and manage virtual machines that run the Spring Boot application and H2 database. 
The setup process involves defining the VM specifications, provisioning scripts, and network settings within the Vagrantfile, similar to how it is done with VirtualBox. 
This allows for an efficient and streamlined virtualization process on Windows systems, leveraging Hyper-V's performance benefits.


The Vagrant file defines the configuration settings for creating virtual machines with Hyper-V.
```bash
Vagrant.configure("2") do |config|
  # Specify the base box. Make sure this box is compatible with Hyper-V.
  config.vm.box = "hypervv/Ubuntu2004"

  # Default provider configuration
  config.vm.provider "hyperv" do |hv|
    hv.cpus = 2          # Number of CPUs
    hv.memory = 1024     # Memory size in MB
    hv.maxmemory = 2048  # Maximum dynamic memory in MB
    hv.linked_clone = true # Use linked clones to speed up VM creation
  end

  # General provisioning script that runs on all VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-17-jdk-headless
  SHELL

  # Configuration for the database VM
  config.vm.define "db" do |db|
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # Specific provisioning for the database VM
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
      java -cp h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/h2-server.log 2>&1 &
    SHELL
  end

  # Configuration for the webserver VM
  config.vm.define "web" do |web|
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # Webserver specific provisioning
    web.vm.provision "shell", inline: <<-SHELL
      sudo apt install -y tomcat9 tomcat9-admin
      # Clone the repository
      git clone https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo2.git
      sudo chown -R vagrant:vagrant /home/vagrant/devops-23-24-RFA-1231856/

      cd devops-23-24-RFA-1231856/CA2/Part2/
      chmod +x ./gradlew
      ./gradlew build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
  end
end
```

### Running the Virtual Environment

1. Enable Hyper-V in your computer. This tool is already available in 64-bit versions of Windows 10, Enterprise, and Education, but not in Home edition.

2. Navigate to the directory containing the Vagrantfile:

3. Run the command \`vagrant up\` to create the virtual machines and provision them.

4. Access the web server by opening a browser and navigating to the IP address of the web server virtual machine followed by the port number 8080.

5. Access the H2 database by opening a browser and navigating to the IP address of the database virtual machine followed by the port number 8082 or 9092.

### Conclusion
This README provides a tutorial for setting up a virtual environment using Vagrant with Hyper-V as the provider.
It summarizes the configuration settings and steps in order to execute a Spring Boot application tutorial within this environment.

