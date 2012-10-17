/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm;

import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterList;
import com.axemblr.service.cm.models.clusters.ClusterRef;
import com.axemblr.service.cm.models.clusters.ClusterVersion;
import com.axemblr.service.cm.models.clusters.ServiceRef;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.cm.RoleState;
import com.axemblr.service.cm.models.cm.RoleType;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceSetupInfo;
import com.axemblr.service.cm.models.cm.ServiceSetupList;
import com.axemblr.service.cm.models.cm.ServiceState;
import com.axemblr.service.cm.models.cm.ServiceType;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.hosts.Host;
import com.axemblr.service.cm.models.hosts.HostList;
import com.axemblr.service.cm.models.hosts.HostRef;
import com.axemblr.service.cm.models.tools.EchoMessage;
import com.axemblr.service.cm.models.tools.ErrorMessage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.hamcrest.Matcher;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Live tests for Cloudera Manager client. Depends on Vagrant being installed on the machine.
 * <p/>
 * The test runs vagrant commands to start and stop the VM by creating an external process.
 * The tests assume the Cloudera Manager will be live on a given URL. If for some reason (vagrant or virtual-box is
 * missing, networking issues, etc. ) Cloudera Manager is not accessible the tests will be ignored.
 */
public class ClouderaManagerClientLiveTest extends LiveTestBase {

  private static final Logger LOGGER = Logger.getLogger(ClouderaManagerClientLiveTest.class.getName());
  private static ClouderaManagerClient CLIENT;

  private final String CLUSTER_NAME = "cl-1";
  private final String HDFS_SERVICE_NAME = "hdfs1";

  @BeforeClass()
  public static void setUpResources() throws Exception {
    //start the VM
    processBuilder.directory(new File(VAGRANT_CONFIG_PATH).getCanonicalFile());
    runAndWait(processBuilder, 5, VAGRANT_CMD, "up");
    TimeUnit.SECONDS.sleep(30);// wait until Cloudera Manager Service starts
    CLIENT = ClouderaManagerClient.withConnectionURI(ENDPOINT).withAuth("admin", "admin").build();
    CLIENT.enableHttpLogging(true);
  }

  @AfterClass
  public static void tearDown() throws Exception {
    runAndWait(processBuilder, 3, VAGRANT_CMD, "destroy", "--force");
    EXECUTOR.shutdown();
  }

  @Before
  public void setUp() throws Exception {
    // this will ignore tests if the endpoint is not accessible
    assumeTrue(isEndpointActive(ENDPOINT));
  }

  @Test
  public void testToolsEchoAPI() throws Exception {
    EchoMessage response = CLIENT.tools().echo("test");
    assertEquals("test", response.getMessage());
  }

  @Test
  public void testToolsEchoErrorAPI() throws Exception {
    ErrorMessage response = null;
    try {
      CLIENT.tools().echoError("my error");
      fail("Exception not thrown!");
    } catch (UniformInterfaceException e) {
      assertEquals(500, e.getResponse().getStatus());
      response = e.getResponse().getEntity(ErrorMessage.class);
    }
    assertEquals("my error", response.getMessage());
  }

  @Test
  public void testClustersSize() throws Exception {
    ClusterList clusters = CLIENT.clusters().getAllClusters();
    assertEquals(0, clusters.getItems().size());
  }

  @Test
  public void testAddHosts() throws Exception {
    HostList hosts = null;
    try {
      hosts = CLIENT.hosts().createHosts(Sets.newHashSet(
          new Host(CM_SERVICE_IP, TARGET_HOST_NAME)));
      assertEquals(1, hosts.getItems().size());
      assertEquals(CM_SERVICE_IP, hosts.getItems().asList().get(0).getIpAddress());
      LOGGER.info(hosts.toString());
    } catch (UniformInterfaceException e) {
      LOGGER.info(e.getResponse().getEntity(ErrorMessage.class).toString());
    }
  }

  @Test
  public void testCreateCluster() throws Exception {
    // create the cluster
    Cluster cluster = new Cluster(CLUSTER_NAME, ClusterVersion.CDH3);
    ClusterList created = CLIENT.clusters().createClusters(Sets.newHashSet(cluster));
    assertEquals(1, created.getItems().size());
    Cluster actual = created.getItems().asList().get(0);
    assertEquals(CLUSTER_NAME, actual.getName());
    LOGGER.info("CLuster created as: " + actual.toString());

    // create a HDFS Service
    ServiceSetupInfo newServiceInfo = new ServiceSetupInfo(HDFS_SERVICE_NAME, ServiceType.HDFS);

    ServiceList serviceList = CLIENT.clusters().getCluster(CLUSTER_NAME).registerServices(new ServiceSetupList(newServiceInfo));
    LOGGER.info("service list " + serviceList);

    assertEquals(1, serviceList.getItems().size());
    Service hdfsService = serviceList.getItems().asList().get(0);
    assertEquals(HDFS_SERVICE_NAME, hdfsService.getName());
    assertEquals(ServiceType.HDFS, hdfsService.getType());
    assertEquals(new ClusterRef(CLUSTER_NAME), hdfsService.getClusterRef());
    assertEquals(ServiceState.STOPPED, hdfsService.getServiceState());

    // assign HDFS roles to machines, the hosts to which we install must exist before this step.
    Role nameNodeSetup = new Role("hdfs-nn", RoleType.NAMENODE, new HostRef(TARGET_HOST_NAME));
    Role secondaryNameNodeSetup = new Role("hdfs-secondary", RoleType.SECONDARYNAMENODE, new HostRef(TARGET_HOST_NAME));
    Role dataNodeSetup = new Role("hdfs-data1", RoleType.DATANODE, new HostRef(TARGET_HOST_NAME));

    RoleList createdRoles = CLIENT.clusters().getCluster(CLUSTER_NAME).getService(HDFS_SERVICE_NAME).createRoles(
        new RoleList(Sets.newHashSet(nameNodeSetup, secondaryNameNodeSetup, dataNodeSetup)));
    LOGGER.info("created roles: " + createdRoles);
    for (Role role : createdRoles.getItems()) {
      assertEquals(new ServiceRef(CLUSTER_NAME, HDFS_SERVICE_NAME), role.getServiceRef());
      assertEquals(new HostRef(TARGET_HOST_NAME), role.getHostRef());
      assertThat(role.getRoleState(), equalTo(RoleState.STOPPED));
      assertThat(role.getType(), anyOf(
          Lists.<Matcher<? extends RoleType>>newArrayList(
              equalTo(RoleType.DATANODE),
              equalTo(RoleType.NAMENODE),
              equalTo(RoleType.SECONDARYNAMENODE))));
    }
    // configure the roles for HDFS

    //TODO: add the config names as constants (Enum or Interface)
    Config nameNodeDataDir = new Config(DFS_NAME_DIR_LIST, "/tmp/nn");
    ConfigList configList = CLIENT.clusters().getCluster(CLUSTER_NAME).getService(HDFS_SERVICE_NAME)
        .updateRoleConfig("hdfs-nn", new ConfigList(nameNodeDataDir));
    assertThat(configList.getItems().size(), equalTo(1));
    assertThat(configList.getItems().get(0).getName(), equalTo(DFS_NAME_DIR_LIST));
    assertThat(configList.getItems().get(0).getValue(), equalTo("/tmp/nn"));

    Config hdfsCheckPointDir = new Config(FS_CHECKPOINT_DIR_LIST, "/tmp/checkpoints");
    configList = CLIENT.clusters().getCluster(CLUSTER_NAME).getService(HDFS_SERVICE_NAME)
        .updateRoleConfig("hdfs-secondary", new ConfigList(hdfsCheckPointDir));

    assertThat(configList.getItems().size(), equalTo(1));
    assertThat(configList.getItems().get(0).getName(), equalTo(FS_CHECKPOINT_DIR_LIST));
    assertThat(configList.getItems().get(0).getValue(), equalTo("/tmp/checkpoints"));


    Config datanodeDataDir = new Config(DFS_DATA_DIR_LIST, "/tmp/datanode");
    configList = CLIENT.clusters().getCluster(CLUSTER_NAME).getService(HDFS_SERVICE_NAME)
        .updateRoleConfig("hdfs-data1", new ConfigList(datanodeDataDir));

    assertThat(configList.getItems().size(), equalTo(1));
    assertThat(configList.getItems().get(0).getName(), equalTo(DFS_DATA_DIR_LIST));
    assertThat(configList.getItems().get(0).getValue(), equalTo("/tmp/datanode"));

    BulkCommandList commands = CLIENT.clusters().getCluster(CLUSTER_NAME)
        .getService(HDFS_SERVICE_NAME).hdfsFormat(new RoleNameList("hdfs-nn"));
    LOGGER.info("Commands for formatting HDFS: " + commands.toString());
    List<Command> result = CLIENT.commands().waitFor(commands);

    for (Command c : result) {
      assertThat(c.isActive(), equalTo(false));
      assertThat(c.isSuccess(), equalTo(true));
    }
    LOGGER.info("HDFS formatted successfully!" + result);
    // start the service
    Command command = CLIENT.clusters().getCluster(CLUSTER_NAME).getService(HDFS_SERVICE_NAME).start();
    LOGGER.info("Command " + command.toString());
    command = CLIENT.commands().waitFor(command);
    assertThat(command.isActive(), equalTo(false));
    assertThat(command.isSuccess(), equalTo(true));
    LOGGER.info("HDFS cluster started successfully ! " + command);
    // simple nasty test to see if HDFS is up
    String nameNodeStatus = CharStreams.toString(new InputStreamReader(new URL("http://" + CM_SERVICE_IP + ":" + "50070/dfshealth.jsp").openStream()));
    assertThat(nameNodeStatus, containsString("NameNode Storage"));
    assertThat(nameNodeStatus, containsString("Active"));
  }
}
