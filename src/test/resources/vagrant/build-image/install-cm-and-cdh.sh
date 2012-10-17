#!/bin/sh 
# 
# Provisioning script for a vagrant machine. 
#

# install Cloduera Repo and update the lists
wget http://archive.cloudera.com/cm4/installer/latest/cloudera-manager-installer.bin
chmod u+x cloudera-manager-installer.bin

update-locale LANG=en_US.UTF-8
export LANG=en_US.utf8
export LC_ALL=en_US.UTF-8
export LANGUAGE=en_US

# we need expect to install cloudera manager
apt-get install -y expect

/usr/bin/expect -c "set timeout 600; spawn ./cloudera-manager-installer.bin --ui=stdio --noprompt --noreadme --nooptions --i-agree-to-all-licenses; expect EOF"

# Register Cloudera package repositories
cat > /etc/apt/sources.list.d/cloudera-cdh3.list <<END
deb http://archive.cloudera.com/debian lucid-cdh3 contrib
END

wget http://extjs.com/deploy/ext-2.2.zip
mkdir -p /usr/lib/oozie/libext && cp ext-2.2.zip /usr/lib/oozie/libext/

# Install all the required packages for CDH3 (Core Hadoop) components
apt-get update && apt-get install -y --force-yes oracle-j2sdk1.6 hadoop-0.20 hadoop-0.20-native hadoop-hive hadoop-pig oozie oozie-client
apt-get install -y --force-yes cloudera-manager-daemons cloudera-manager-agent

# disable oozie auto-start as part of CDH
update-rc.d -f oozie remove


