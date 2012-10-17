/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models;

import com.axemblr.service.cm.SerializationTest;
import com.axemblr.service.cm.models.clusters.Activity;
import com.axemblr.service.cm.models.clusters.ActivityList;
import com.axemblr.service.cm.models.clusters.ActivityStatus;
import com.axemblr.service.cm.models.clusters.ActivityType;
import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterRef;
import com.axemblr.service.cm.models.clusters.ClusterVersion;
import com.axemblr.service.cm.models.clusters.HdfsDisableHaArguments;
import com.axemblr.service.cm.models.clusters.HdfsFailoverArguments;
import com.axemblr.service.cm.models.clusters.HdfsHaArguments;
import com.axemblr.service.cm.models.clusters.HealthCheck;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.clusters.NameService;
import com.axemblr.service.cm.models.clusters.NameServiceList;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.clusters.ServiceRef;
import com.axemblr.service.cm.models.cm.RoleRef;
import com.axemblr.service.cm.models.cm.RoleType;
import com.axemblr.service.cm.models.cm.RoleTypeConfig;
import com.axemblr.service.cm.models.events.Event;
import com.axemblr.service.cm.models.events.EventAttribute;
import com.axemblr.service.cm.models.events.EventCategory;
import com.axemblr.service.cm.models.events.EventQueryResult;
import com.axemblr.service.cm.models.events.EventSeverity;
import com.axemblr.service.cm.models.hosts.Host;
import com.axemblr.service.cm.models.hosts.HostRef;
import com.axemblr.service.cm.models.tools.EchoMessage;
import com.axemblr.service.cm.models.tools.ErrorMessage;
import com.axemblr.service.cm.models.users.User;
import java.io.File;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test Cloudera Manager data models serialization/de-serialization.
 */
public class ClusterClassesSerializationTest extends SerializationTest {

  @Test
  public void testUser() throws Exception {
    User user = mapper.readValue(new File("src/test/resources/user.json"), User.class);
    assertEquals("user", user.getName());
    assertEquals("pass", user.getPassword());
    assertEquals(2, user.getRoles().size());
    assertEquals("role1", user.getRoles().asList().get(0));
    assertEquals("role2", user.getRoles().asList().get(1));
  }

  @Test
  public void testEvent() throws Exception {
    Event event = mapper.readValue(new File("src/test/resources/event.json"), Event.class);
    assertEquals("123", event.getId());
    assertEquals("missing content", event.getContent());
    assertEquals(1224, event.getTimeOccurred());
    assertEquals(1249, event.getTimeReceived());
    assertEquals(EventCategory.UNKNOWN, event.getCategory());
    assertEquals(EventSeverity.UNKNOWN, event.getSeverity());
    assertFalse(event.isAlert());
    assertEquals(1, event.getAttributes().size());
    EventAttribute eventAttribute = event.getAttributes().asList().get(0);
    assertEquals("revision", eventAttribute.getName());
    assertEquals("11", eventAttribute.getValues().asList().get(1));
    assertEquals("22", eventAttribute.getValues().asList().get(0));
  }

  @Test
  public void testEchoMessage() throws Exception {
    EchoMessage message = mapper.readValue(new File("src/test/resources/echo-message.json"), EchoMessage.class);
    assertEquals("echo echo echo", message.getMessage());
  }

  @Test
  public void testHdfsDisableHaArgs() throws Exception {
    HdfsDisableHaArguments args = mapper.readValue(new File("src/test/resources/hdfs-disable-ha-args.json"),
        HdfsDisableHaArguments.class);
    assertEquals("primary", args.getActiveName());
    assertEquals("secondary", args.getSecondaryName());
    assertFalse(args.shouldRedeployClientConfigs());
    assertFalse(args.shouldRestartDependentServices());
  }

  @Test
  public void testHdfsHaArguments() throws Exception {
    HdfsHaArguments haArguments = mapper.readValue(new File("src/test/resources/hdfs-ha-arguments.json"),
        HdfsHaArguments.class);
    assertEquals("active-01", haArguments.getActiveName());
    assertEquals("standby-01", haArguments.getStandByName());
    assertEquals("/share/edits", haArguments.getActiveSharedEditsPath());
    assertEquals("/share/edits/standby", haArguments.getStandBySharedEditsPath());
    assertEquals("name-service", haArguments.getNameService());
    assertTrue(haArguments.shouldRestartDependentServices());
    assertFalse(haArguments.shouldRedeployClientConfigs());
  }

  @Test
  public void testHdfsFailoverArgs() throws Exception {
    HdfsFailoverArguments failoverArguments = mapper.readValue(new File("src/test/resources/hdfs-failover-args.json"),
        HdfsFailoverArguments.class);
    assertEquals("name-service-01", failoverArguments.getNameservice());
    assertEquals("active-fc", failoverArguments.getActiveFCName());
    assertEquals("standby-fc", failoverArguments.getStandByFCName());
    ServiceRef zookeeperService = failoverArguments.getZooKeeperService();
    assertEquals("cluster-01", zookeeperService.getClusterName());
    assertEquals("foo-bar", zookeeperService.getServiceName());
  }

  @Test
  public void testHealthCheck() throws Exception {
    HealthCheck healthCheck = mapper.readValue(new File("src/test/resources/health-check.json"), HealthCheck.class);
    assertEquals("healthy-name-node", healthCheck.getName());
    assertEquals(HealthSummary.DISABLED, healthCheck.getSummary());
    assertEquals(6, HealthSummary.values().length);
  }

  @Test
  public void testServiceRef() throws Exception {
    ServiceRef serviceRef = mapper.readValue(new File("src/test/resources/service-ref.json"), ServiceRef.class);
    assertEquals("cluster-01", serviceRef.getClusterName());
    assertEquals("service-01", serviceRef.getServiceName());
  }

  @Test
  public void testClusterRef() throws Exception {
    ClusterRef clusterRef = mapper.readValue(new File("src/test/resources/cluster-ref.json"), ClusterRef.class);
    assertEquals("cluster-01", clusterRef.getClusterName());
  }

  @Test
  public void testRoleRef() throws Exception {
    RoleRef roleRef = mapper.readValue(new File("src/test/resources/role-ref.json"), RoleRef.class);
    assertEquals("cluster-01", roleRef.getClusterName());
    assertEquals("service-01", roleRef.getServiceName());
    assertEquals("primary-role", roleRef.getRoleName());
  }

  @Test
  public void testEchoError() throws Exception {
    ErrorMessage error = mapper.readValue(new File("src/test/resources/echo-error.json"), ErrorMessage.class);
    assertThat(error.getMessage(), is("An error message"));
    assertThat(error.getCauses().size(), equalTo(2));
  }

  @Test
  public void testHost() throws Exception {
    Host host = mapper.readValue(new File("src/test/resources/host.json"), Host.class);
    assertEquals("123", host.getHostId());
    assertEquals("192.168.0.1", host.getIpAddress());
    assertEquals("host123", host.getHostname());
    assertEquals("555", host.getRackId());
    assertEquals(new DateTime("2012-05-06T20:56:57.918Z", DateTimeZone.UTC), host.getLastHeartbeat());
    assertEquals(HealthSummary.DISABLED, host.getHealthSummary());
    assertEquals("http://axemblr.com", host.getHostUrl());
    assertEquals(1, host.getRoleRefs().size());
    assertEquals(1, host.getHealthChecks().size());
  }

  @Test
  public void testHostRef() throws Exception {
    HostRef hostRef = mapper.readValue(new File("src/test/resources/host-ref.json"), HostRef.class);
    assertEquals("unique-id", hostRef.getHostId());
  }

  @Test
  public void testCluster() throws Exception {
    Cluster cluster = mapper.readValue(new File("src/test/resources/cluster.json"), Cluster.class);
    assertEquals("cluster-01", cluster.getName());
    assertEquals(ClusterVersion.CDH3, cluster.getVersion());
  }

  @Test
  public void testActivity() throws Exception {
    Activity activity = mapper.readValue(new File("src/test/resources/activity.json"), Activity.class);
    assertEquals("activity-01", activity.getName());
    assertEquals(ActivityType.UNKNOWN, activity.getType());
    assertEquals("activity-00", activity.getParent());
    assertEquals(111, activity.getStartTime());
    assertEquals(999, activity.getFinishTime());
    assertEquals(ActivityStatus.UNKNOWN, activity.getStatus());
    assertEquals("user-01", activity.getUser());
    assertEquals("group-01", activity.getGroup());
  }

  @Test
  public void testEventQueryResult() throws Exception {
    EventQueryResult queryResult = mapper.readValue(new File("src/test/resources/event-query-result.json"),
        EventQueryResult.class);
    assertEquals(1, queryResult.getTotalResults());
    assertEquals(1, queryResult.getItems().size());
    Event event = queryResult.getItems().asList().get(0);
    assertEquals("11", event.getId());
    assertEquals(1, event.getAttributes().size());
  }

  @Test
  public void testNameServiceList() throws Exception {
    NameServiceList list = mapper.readValue(new File("src/test/resources/name-service-list.json"), NameServiceList.class);
    assertEquals(1, list.getItems().size());
    NameService nameService = list.getItems().asList().get(0);
    assertEquals("name-service-01", nameService.getName());
    RoleRef activeNameNode = nameService.getActive();
    assertEquals("cluster-01", activeNameNode.getClusterName());
    assertEquals("service-01", activeNameNode.getServiceName());
    assertEquals("active", activeNameNode.getRoleName());
    assertEquals(2, nameService.getMountPoints().size());
    assertEquals("/mount/point/1", nameService.getMountPoints().asList().get(1));
    assertEquals(HealthSummary.DISABLED, nameService.getHealthSummary());
    HealthCheck healthCheck = nameService.getHealthChecks().asList().get(0);
    assertEquals(HealthSummary.DISABLED, healthCheck.getSummary());
    assertEquals("healthy-name-node", healthCheck.getName());
  }

  @Test
  public void testServiceConfig() throws Exception {
    ServiceConfig config = mapper.readValue(new File("src/test/resources/service-config.json"), ServiceConfig.class);
    assertEquals(1, config.getRoleTypeConfigs().size());
    assertEquals(1, config.getItems().size());
    RoleTypeConfig roleTypeConfig = config.getRoleTypeConfigs().asList().get(0);
    assertEquals(RoleType.MASTER, roleTypeConfig.getRoleType());
  }

  @Test
  public void testActivityList() throws Exception {
    ActivityList activityList = mapper.readValue(new File("src/test/resources/activity-list.json"), ActivityList.class);
    assertEquals(1, activityList.getItems().size());
    assertEquals("activity-01", activityList.getItems().get(0).getName());
  }
}