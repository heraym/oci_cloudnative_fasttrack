#Install Java
cd ~/java
tar -xvzf ~/java/openjdk-14.0.2_linux-x64_bin.tar.gz

#Install Maven
mkdir ~/maven
wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz -P /tmp
tar xf /tmp/apache-maven-3.6.0-bin.tar.gz -C ~/maven

#Env Variables
sudo cp helidon.sh /etc/profile.d/
sudo chmod +x /etc/profile.d/helidon.sh
source /etc/profile.d/helidon.sh
