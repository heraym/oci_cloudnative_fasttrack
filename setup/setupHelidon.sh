#Install Java
tar -xvzf ~/java/jdk-11.0.7_linux-x64_bin.tar.gz

#Install Maven
mkdir ~/maven
wget https://www-us.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz -P /tmp
sudo tar xf /tmp/apache-maven-3.6.0-bin.tar.gz -C ~/maven

#Env Variables
cp helidon.sh /etc/profile.d/
sudo chmod +x /etc/profile.d/helidon.sh
source /etc/profile.d/helidon.sh
