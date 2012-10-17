Cloudera Manager API client
===========================

Java client for Cloudera Manager Rest interface


Things to consider
==================

We designed the API to work with CM 4.0+ (v1 of the API) and we used it to deploy a cluster with Cloudera Manager 4.0.4 via API. 

Features
========

 * the interface is very close to the original API 
 * very good JavaDoc documentation
 * all created objects are immutable => thread safe
 * fluent API 

How to use
==========

Create a client instance:

~~~Java
    private final URI ENDPOINT = URI.create("http://<cloudera-manager-service>:7180"));
    
    ClouderaManagerClient CLIENT = ClouderaManagerClient.withConnectionURI(ENDPOINT)
                                                        .withAuth("admin", "admin").build();
~~~

and use it to issue requests to the server:

~~~Java
    // add a host to the pool
    HostList hosts = null;
    try {
      hosts = CLIENT.hosts().createHosts(Sets.newHashSet(
          new Host(CM_SERVICE_IP, TARGET_HOST_NAME)));
    } catch (UniformInterfaceException e) {
      LOGGER.info(e.getResponse().getEntity(ErrorMessage.class).toString());
    }
    
    // create a cluster
    Cluster cluster = new Cluster(CLUSTER_NAME, ClusterVersion.CDH3);
    ClusterList created = CLIENT.clusters().createClusters(Sets.newHashSet(cluster));
~~~

Calls will return immediatly and you will have to interrogate Cloudera Manaer for the result of 
the call as it might take some time before the commands issued will finish. 

Fortunatelly it's easy, all you have to do is use waitFor( <commands> ) method: 

~~~Java
    // issue an expensive command that will take some time to finish
    
    BulkCommandList commands = CLIENT.clusters().getCluster(CLUSTER_NAME)
        .getService(HDFS_SERVICE_NAME).hdfsFormat(new RoleNameList("hdfs-nn"));
    LOGGER.info("Commands for formatting HDFS: " + commands.toString());
    
    // wait for them to finish and get the results
    List<Command> result = CLIENT.commands().waitFor(commands);
~~~

See [ClouderaManagerClientLiveTest.java](https://github.com/andreisavu/cloudera-manager-api/blob/master/src/test/java/com/axemblr/service/cm/ClouderaManagerClientLiveTest.java) 
for examples on how to create a cluster and add services to it via Java Client API calls. 

Testing with Vagrant
====================

Integration tests can be don using Vagrant. Vagrant is a VirtualBox driver written in Ruby. You will need to install
VirtualBox and Ruby, before Vagrant. The process is explained in an
[article](http://blog.axemblr.com/2012/10/09/java-client-for-cloudera-manager-api---automate-hadoop-deployment/) on our
[blog](http://blog.axemblr.com).

* Install Vagrant on your machine from [http://vagrantup.com/] 
* get an Ubuntu Lucid amd64 box from http://files.vagrantup.com/lucid64.box
* install Cloudera Manager 4 following instructions from [Cloudera Manager Instalation Guide](https://ccp.cloudera.com/display/FREE4DOC/Cloudera+Manager+Free+Edition+Installation+Guide)
* update the [Vagrant config file](https://github.com/andreisavu/cloudera-manager-api/blob/master/src/test/resources/vagrant/Vagrantfile) with the name of the box
* run tests with $ mvn clean test -Pwith-live-tests

Resources
=========

 * http://cloudera.github.com/cm_api/
 * http://www.cloudera.com/products-services/tools/
 * http://www.cloudera.com/blog/2012/09/automating-your-cluster-with-cloudera-manager-api/
 * http://blog.axemblr.com/2012/10/09/java-client-for-cloudera-manager-api---automate-hadoop-deployment/


