package com.axemblr.service.cm;

import com.axemblr.service.cm.apis.ClusterAPI;
import com.axemblr.service.cm.apis.ServiceAPI;
import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterList;
import com.axemblr.service.cm.models.clusters.ClusterVersion;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.cm.RoleType;
import com.axemblr.service.cm.models.cm.RoleTypeConfig;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceSetupInfo;
import com.axemblr.service.cm.models.cm.ServiceSetupList;
import com.axemblr.service.cm.models.cm.ServiceType;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.hosts.HostRef;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class OozieInstallLiveTest extends LiveTestBase {

  private static final Logger LOGGER = Logger.getLogger(OozieInstallLiveTest.class.getName());
  private static ClouderaManagerClient CLIENT;
  private static final String CLUSTER_NAME = "cluster1";
  private static final String HDFS_SERVICE_NAME = "hdfs1";
  private final String OOZIE_SERVICE_NAME = "oozie";

  @BeforeClass
  public static void setUpEnvironment() throws Exception {
    //start the VM
    processBuilder.directory(new File(VAGRANT_CONFIG_PATH).getCanonicalFile());
    runAndWait(processBuilder, 5, VAGRANT_CMD, "up");
    TimeUnit.SECONDS.sleep(40);// wait until Cloudera Manager Service starts
    CLIENT = ClouderaManagerClient.withConnectionURI(ENDPOINT).withAuth("admin", "admin").build();
    CLIENT.enableHttpLogging(true);
  }

  @AfterClass
  public static void tearDownEnvironment() throws Exception {
    runAndWait(processBuilder, 3, VAGRANT_CMD, "destroy", "--force");
    EXECUTOR.shutdown();
  }

  @Before
  public void setUp() throws Exception {
    assumeTrue(isEndpointActive(ENDPOINT));
  }

  @Test
  public void testInstallOozie() throws Exception {
    // create the cluster
    Cluster cluster = new Cluster(CLUSTER_NAME, ClusterVersion.CDH3);

    ClusterList created = CLIENT.clusters().createClusters(Sets.newHashSet(cluster));
    assertEquals(1, created.getItems().size());

    // create a HDFS Service
    ServiceSetupInfo newServiceInfo = new ServiceSetupInfo(HDFS_SERVICE_NAME, ServiceType.HDFS);
    ClusterAPI clusterApi = CLIENT.clusters().getCluster(CLUSTER_NAME);
    ServiceList serviceList = clusterApi.registerServices(new ServiceSetupList(newServiceInfo));
    LOGGER.info("service list " + serviceList);

    // assign HDFS roles to machines, the hosts to which we install must exist before this step.
    Role nameNodeSetup = new Role("hdfs-nn", RoleType.NAMENODE, new HostRef(TARGET_HOST_NAME));
    Role secondaryNameNodeSetup = new Role("hdfs-secondary", RoleType.SECONDARYNAMENODE, new HostRef(TARGET_HOST_NAME));
    Role dataNodeSetup = new Role("hdfs-data1", RoleType.DATANODE, new HostRef(TARGET_HOST_NAME));

    ServiceAPI hdfsService = clusterApi.getService(HDFS_SERVICE_NAME);
    RoleList createdRoles = hdfsService.createRoles(new RoleList(Sets.newHashSet(nameNodeSetup, secondaryNameNodeSetup, dataNodeSetup)));
    LOGGER.info("created roles: " + createdRoles);

    // configure the roles for HDFS
    Config nameNodeDataDir = new Config(DFS_NAME_DIR_LIST, "/tmp/nn");
    ConfigList configList = hdfsService.updateRoleConfig("hdfs-nn", new ConfigList(nameNodeDataDir));

    Config hdfsCheckPointDir = new Config(FS_CHECKPOINT_DIR_LIST, "/tmp/checkpoints");
    hdfsService.updateRoleConfig("hdfs-secondary", new ConfigList(hdfsCheckPointDir));

    Config datanodeDataDir = new Config(DFS_DATA_DIR_LIST, "/tmp/datanode");
    hdfsService.updateRoleConfig("hdfs-data1", new ConfigList(datanodeDataDir));

    BulkCommandList commands = clusterApi.getService(HDFS_SERVICE_NAME).hdfsFormat(new RoleNameList("hdfs-nn"));
    LOGGER.info("Commands for formatting HDFS: " + commands.toString());
    List<Command> result = CLIENT.commands().waitFor(commands);

    LOGGER.info("HDFS formatted successfully!" + result);
    // start the service
    Command startCommand = hdfsService.start();
    assertThat(CLIENT.commands().waitFor(startCommand).isSuccess(), is(true));

    /**
     * Now we configure Oozie and make it work with HDFS.
     */

    // register Oozie service
    ServiceSetupInfo serviceSetupInfo = new ServiceSetupInfo(OOZIE_SERVICE_NAME, ServiceType.OOZIE);
    serviceList = clusterApi.registerServices(new ServiceSetupList(serviceSetupInfo));

    ServiceAPI oozieService = clusterApi.getService(OOZIE_SERVICE_NAME);
    // assign the Oozie role to a host
    Role oozieRole = new Role("oozie", RoleType.OOZIE_SERVER, new HostRef(TARGET_HOST_NAME));
    oozieService.createRoles(new RoleList(Sets.newHashSet(oozieRole)));

    // configure oozie
    configList = oozieService.getConfig(true);
    LOGGER.info(configList.toString());
    List<Config> oozieRoleLevelConfig = Lists.newArrayList(new Config("oozie_web_console", "true"));
    oozieService.updateConfig(new ServiceConfig(null,
        Sets.newHashSet(new RoleTypeConfig(oozieRoleLevelConfig, RoleType.OOZIE_SERVER))));
    LOGGER.info(oozieService.getConfig(true).toString());
    assertTrue(CLIENT.commands().waitFor(oozieService.start()).isSuccess());
  }

}
