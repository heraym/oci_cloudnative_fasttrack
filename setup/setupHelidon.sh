#!/bin/bash

set -x

#Install Java
mkdir ~/java
wget https://download.java.net/java/GA/jdk14.0.2/205943a0976c4ed48cb16f1043c5c647/12/GPL/openjdk-14.0.2_linux-x64_bin.tar.gz -P /tmp
tar xf /tmp/openjdk-14.0.2_linux-x64_bin.tar.gz -C ~/java

#Install Maven
mkdir ~/maven
wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz -P /tmp
tar xf /tmp/apache-maven-3.6.3-bin.tar.gz -C ~/maven

#Env Variables
sudo cp ~/oci_cloudnative_fasttrack/setup/helidon.sh /etc/profile.d/
sudo chmod +x /etc/profile.d/helidon.sh
source /etc/profile.d/helidon.sh
