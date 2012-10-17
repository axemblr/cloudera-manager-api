/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm;

import com.axemblr.service.cm.ClouderaManagerClient;
import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterList;
import com.axemblr.service.cm.models.clusters.ClusterVersion;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.clusters.ServiceRef;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleState;
import com.axemblr.service.cm.models.cm.RoleType;
import com.axemblr.service.cm.models.cm.RoleTypeConfig;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceSetupInfo;
import com.axemblr.service.cm.models.cm.ServiceSetupList;
import com.axemblr.service.cm.models.cm.ServiceState;
import com.axemblr.service.cm.models.cm.ServiceType;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.hosts.HostRef;
import com.axemblr.service.cm.models.hosts.MetricList;
import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * API tests using a mock web server.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public class MockClouderaManagerTest extends SerializationTest {

  private final MockWebServer server = new MockWebServer();

  private static MockResponse mockResponseFromJsonFile(String jsonFile) throws IOException {
    String contents = Files.toString(new File(jsonFile), Charsets.UTF_8);
    return new MockResponse().setBody(contents).setHeader("Content-type", MediaType.APPLICATION_JSON);
  }

  /**
   * Test for request:
   * $ curl -u admin:admin 'http://localhost:7180/api/v1/clusters'
   *
   * @throws Exception
   */
  @Test
  public void testName() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/clusters.json"));
    server.play();

    URL url = server.getUrl("/");

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(url.toString()).build();

    ClusterList clusters = client.clusters().getAllClusters();

    assertEquals(2, clusters.getItems().size());
    assertTrue(Iterables.all(clusters.getItems(), new Predicate<Cluster>() {
      @Override
      public boolean apply(Cluster input) {
        return input.getVersion() == ClusterVersion.CDH3 || input.getVersion() == ClusterVersion.CDH4;
      }
    }));
  }

  /**
   * Test for request:
   * $ curl -u admin:admin 'http://localhost:7180/api/v1/clustes/Cluster%201%20-%20CDH4/services'
   *
   * @throws Exception
   */
  @Test
  public void testClusterServices() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/cluster-services.json"));
    server.play();

    URL url = server.getUrl("/");
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(url.toString()).build();

    ServiceList list = client.clusters().getCluster("Cluster 1 - CDH4").getServices();
    assertEquals(1, list.getItems().size());
    Service service = list.getItems().asList().get(0);
    assertEquals(ServiceState.STARTED, service.getServiceState());
    assertEquals("hdfs1", service.getName());
    assertEquals(ServiceType.HDFS, service.getType());
    assertEquals(HealthSummary.GOOD, service.getHealthSummary());
    assertEquals(9, service.getHealthChecks().size());
  }

  /**
   * Test for request:
   * $ curl -u admin:admin  'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/hdfs1/config'
   *
   * @throws Exception
   */
  @Test
  public void testClusterServiceConfig() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/service-config.json"));
    server.play();

    URL url = server.getUrl("/");
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(url.toString()).build();

    ServiceConfig config = client.clusters().getCluster("Cluster 1 - CDH4").getService("hdfs1").getConfig();
    assertEquals(2, config.getItems().size());
    assertTrue(Iterables.all(config.getItems(), new Predicate<Config>() {
      @Override
      public boolean apply(Config input) {
        return input.getValue().equals("K2TCx9h1sk5XXKW05Pa5Qv0MRDwruO") ||
            input.getValue().equals("zookeeper1");
      }
    }));
    assertEquals(7, config.getRoleTypeConfigs().size());
  }

  /**
   * Test for request:
   * $ curl -u admin:admin 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/hdfs1/config?view=full'
   *
   * @throws Exception
   */
  @Test
  public void testClusterServiceConfigFullView() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/service-config-full.json"));
    server.play();

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(server.getUrl("/").toString()).build();
    // no support for full view
    ServiceConfig config = client.clusters().getCluster("Cluster 1 - CDH4").getService("hdfs1").getConfig();
    assertEquals(0, config.getItems().size());
    assertEquals(1, config.getRoleTypeConfigs().size());
    RoleTypeConfig datanodeConfig = config.getRoleTypeConfigs().asList().get(0);
    assertEquals(RoleType.DATANODE, datanodeConfig.getRoleType());
    assertEquals(3, datanodeConfig.getItems().size());
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -X POST -H "Content-Type:application/json" -u admin:admin \
   * -d '{ "items": [ { "name": "my_hbase", "type": "HBASE" } ] }'  \
   * 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services'
   * <p/>
   * This adds a new HBase service called "my_hbase". The API input is a list of services, for bulk operation.
   * Even though the call creates only one service, it still passes in a list (with one item).
   * The API returns the newly created service.
   * <p/>
   *
   * @throws Exception
   */
  @Test
  public void testAddHbaseService() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/new-service-hbase.json"));
    server.play();

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(server.getUrl("/").toString()).build();

    ServiceSetupInfo serviceInfo = new ServiceSetupInfo("my_hbase", ServiceType.HBASE,
        null, null, null, false, null, null, null, null);
    ServiceSetupList serviceSetupList = new ServiceSetupList(Sets.newHashSet(serviceInfo));
    ServiceList response = client.clusters().getCluster("Cloudera 1 - CDH4").registerServices(serviceSetupList);
    assertEquals(1, response.getItems().size());
    Service service = response.getItems().asList().get(0);

    assertEquals("my_hbase", service.getName());
    assertEquals(ServiceType.HBASE, service.getType());
    assertEquals("Cluster 1 - CDH4", service.getClusterRef().getClusterName());
    assertEquals(ServiceState.STOPPED, service.getServiceState());
    assertFalse(service.isConfigStale());
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -X POST -H "Content-Type:application/json" -u admin:admin \
   * -d '{"items": [
   * { "name": "master1", "type": "MASTER", "hostRef": { "hostId": "localhost" } },
   * { "name": "rs1", "type": "REGIONSERVER", "hostRef": { "hostId": "localhost" } } ] }' \
   * 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/my_hbase/roles'
   * <p/>
   * This creates Master and RegionServer roles. The API returns the newly created roles.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testCreateMasterAndRegionRoles() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/new-master-and-region-roles.json"));
    server.play();

    //TODO: fix configStale issue in Role: missing from the docs, found in example
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionString(server.getUrl("/").toString()).build();
    RoleList createdRoles = client.clusters().getCluster("Cloudera 1 - CDH4").getService("my_hbase")
        .createRoles(new RoleList(Sets.newHashSet(
            new Role("master1", RoleType.MASTER, new HostRef("localhost"), null, null, null, null, false, null, null),
            new Role("rs1", RoleType.REGIONSERVER, new HostRef("localhost"), null, null, null, null, false, null, null)
        )));
    assertEquals(2, createdRoles.getItems().size());
    final ServiceRef serviceRef = new ServiceRef("Cluster 1 - CDH4", "my_hbase");
    final HostRef hostRef = new HostRef("localhost");
    for (Role role : createdRoles.getItems()) {
      assertEquals(RoleState.STOPPED, role.getRoleState());
      assertEquals(serviceRef, role.getServiceRef());
      assertEquals(hostRef, role.getHostRef());
    }
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -X PUT -H "Content-Type:application/json" -u admin:admin \
   * -d '{ "items": [
   * { "name": "hdfs_rootdir", "value": "/my_hbase" },
   * { "name": "zookeeper_service", "value": "zookeeper1" },
   * { "name": "hdfs_service", "value": "hdfs1" } ] }' \
   * 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/my_hbase/config'
   * <p/>
   * This sets the service dependency and HDFS root directory for our newly created HBase Service.
   * The API returns the set of custom configuration.
   *
   * @throws Exception
   */
  @Test
  public void testUpdateServiceConfig() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/update-service-config.json"));
    server.play();

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();
    final Config config1 = new Config("hdfs_rootdir", "/my_hbase", false, null, null, null, null, null, null);
    final Config config2 = new Config("zookeeper_service", "zookeeper1", false, null, null, null, null, null, null);
    final Config config3 = new Config("hdfs_service", "hdfs1", false, null, null, null, null, null, null);

    ServiceConfig config = new ServiceConfig(Lists.newArrayList(config1, config2, config3), null);
    ServiceConfig newConfig = client.clusters().getCluster("Cluster 1 - CDH4").getService("my_hbase").updateConfig(config);

    assertEquals(3, newConfig.getRoleTypeConfigs().size());
    assertEquals(3, newConfig.getItems().size());

    for (Config conf : newConfig.getItems()) {
      assertTrue(conf.equals(config1) || conf.equals(config2) || conf.equals(config3));
    }
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -X POST -u admin:admin \
   * 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/my_hbase/commands/hbaseCreateRoot'
   * <p/>
   * After setting the root directory, we need to create it in HDFS. There is an HBase service level command for that.
   * As with all API command calls, the issued command runs asynchronously. The API returns the command object,
   * which may still be active.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testHBaseRootCommand() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/hbase-create-root-cmd.json"));
    server.play();
    fail("MockWebServer hangs whne receiving POST without body");
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();
    Command command = client.clusters().getCluster("Cloudera 1 - CDH4").getService("my_hbase").createHBaseRoot();

    Command expected = new Command(142L, "CreateRootDir", new DateTime("2012-05-06T20:56:57.918Z", DateTimeZone.UTC),
        null, true, false, null, null, new ServiceRef("my_hbase", "Cluster 1 - CDH4"), null, null, null, null);
    assertEquals(expected, command);
  }

  /**
   * Test for request:
   * <p/>
   * curl -u admin:admin 'http://localhost:7180/api/v1/commands/142'
   * <p/>
   * We can check on the command's status, at the /commands endpoint, to see whether it has finished.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testCheckCommandStatus() throws Exception {
    server.enqueue(mockResponseFromJsonFile("src/test/resources/mock/hbase-create-root-cmd-status.json"));
    server.play();
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();

    Command command = client.commands().details(142);
    assertEquals(142, command.getId());
    assertEquals("CreateRootDir", command.getName());
    assertEquals(1, command.getChildren().getItems().size());
    assertTrue(command.isSuccess());
  }

  /**
   * Test for request:
   * <p/>
   * curl -X POST -u admin:admin 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/my_hbase/commands/start'
   * <p/>
   * We now start the new HBase service.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testHBaseStartService() throws Exception {
    String responseFile = "src/test/resources/mock/hbase-start-service.json";
    server.enqueue(mockResponseFromJsonFile(responseFile));
    server.play();
    fail("MockWebServer hangs whne receiving POST without body");
    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();
    Command command = client.clusters().getCluster("Cloudera 1 - CDH4").getService("my_hbase").start();
    Command expected = mapper.readValue(new File(responseFile), Command.class);
    assertEquals(expected, command);
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -u admin:admin 'http://localhost:7180/api/v1/commands/145'
   * <p/>
   * Again, we poll to check the command's result.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testHBaseStartServiceCommandStatus() throws Exception {
    String responseFile = "src/test/resources/mock/hbase-start-service.json";
    server.enqueue(mockResponseFromJsonFile(responseFile));
    server.play();

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();
    Command command = client.commands().details(145);
    Command expected = mapper.readValue(new File(responseFile), Command.class);
    assertEquals(expected, command);
  }

  /**
   * Test for request:
   * <p/>
   * $ curl -u admin:admin \
   * 'http://localhost:7180/api/v1/clusters/Cluster%201%20-%20CDH4/services/hdfs1/metrics?metrics=dfs_capacity_used_non_hdfs&metrics=dfs_capacity'
   * <p/>
   * In the Enterprise Edition, you can get metrics related to hosts, services, roles and activities.
   * The call by default fetches data points from the last 5 minutes.
   *
   * @throws Exception
   */
  @Test
  @Ignore
  public void testMetrics() throws Exception {
    String responseFile = "src/test/resources/mock/metrics.json";
    server.enqueue(mockResponseFromJsonFile(responseFile));
    server.play();

    ClouderaManagerClient client = ClouderaManagerClient.withConnectionURI(server.getUrl("/").toURI()).build();
    MetricList metrics = client.clusters().getCluster("Cloudera 1 - CDH4").getService("hdfs1")
        .getMetrics(100, 1, "metrics=dfs_capacity_used_non_hdfs&metrics=dfs_capacity");
    // TODO: improve metric support, not urgent since it only affects Cloudera Manager Enterprise
    MetricList expected = mapper.readValue(new File(responseFile), MetricList.class);
    assertEquals(expected, metrics);
  }
}