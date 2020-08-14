#!/bin/bash

set -x

#Install GraalVM
mkdir ~/graalvm
wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.1.0/graalvm-ce-java11-linux-amd64-20.1.0.tar.gz -P /tmp
tar xf /tmp/graalvm-ce-java11-linux-amd64-20.1.0.tar.gz -C ~/graalvm

# Instalar GraalVM Native Image
gu install native-image
sudo yum -y install gcc 
sudo yum -y install libstdc++-static 
sudo yum -y install zlib-devel 


