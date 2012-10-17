/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.ServiceAPI;
import com.axemblr.service.cm.models.clusters.ActivityList;
import com.axemblr.service.cm.models.clusters.HdfsDisableHaArguments;
import com.axemblr.service.cm.models.clusters.HdfsFailoverArguments;
import com.axemblr.service.cm.models.clusters.HdfsHaArguments;
import com.axemblr.service.cm.models.clusters.NameService;
import com.axemblr.service.cm.models.clusters.NameServiceList;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import com.axemblr.service.cm.models.hosts.MetricList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

/**
 * Implementation for Cluster Service API
 */
public class DefaultServiceAPI implements ServiceAPI {

  private final String serviceName;
  private final WebResource resource;

  public DefaultServiceAPI(WebResource clusterResource, String serviceName) {
    this.serviceName = checkNotNull(serviceName);
    this.resource = clusterResource.path("services").path(serviceName);
  }

  public String getServiceName() {
    return serviceName;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public ActivityList getAllActivities(String query, int resultOffset, int maxResults) {
    return resource.path("activities")
        .queryParam("query", query)
        .queryParam("maxResults", Integer.toString(maxResults))
        .queryParam("resultOffset", Integer.toString(resultOffset))
        .get(ActivityList.class);
  }

  @Override
  public ActivityList getActivityChildren(String activityId, int maxResults, int resultOffset) {
    return resource.path("activities").path(activityId).path("children")
        .queryParam("maxResults", Integer.toString(maxResults))
        .queryParam("resultOffset", Integer.toString(resultOffset))
        .get(ActivityList.class);
  }

  @Override
  public MetricList getActivityMetrics(String activityId, long from, long to, String query) {
    return resource.path("activities").path(activityId).path("metrics")
        .queryParam("from", Long.toString(from))
        .queryParam("to", Long.toString(to))
        .queryParam("query", query)
        .get(MetricList.class);
  }

  @Override
  public ActivityList getSimilarActivities(String activityId) {
    return resource.path("activities").path(activityId).path("similar").get(ActivityList.class);
  }

  @Override
  public CommandList getActiveCommands() {
    return resource.path("commands").get(CommandList.class);
  }

  @Override
  public Command decommissionRoles(RoleNameList rolesToDecommission) {
    return resource.path("commands").path("decommission").post(Command.class, rolesToDecommission);
  }

  @Override
  public Command deployClientConfig(RoleNameList rolesToConfigure) {
    return resource.path("commands").path("deployClientConfig").post(Command.class, rolesToConfigure);
  }

  @Override
  public Command createHBaseRoot() {
    return resource.path("commands").path("hbaseCreateRoot").post(Command.class);
  }

  @Override
  public Command disableHdfsAutoFailover() {
    throw new UnsupportedOperationException("Not implemented yet");
    // see request body: http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsDisableAutoFailover.html
    //return resource.path("commands").path("hdfsDisableAutoFailover").post(Command.class);
  }

  @Override
  public Command enableHdfsAutoFailover(HdfsFailoverArguments failoverArguments) {
    return resource.path("commands").path("hdfsEnableAutoFailover").post(Command.class, failoverArguments);
  }

  @Override
  public Command disableHdfsHa(HdfsDisableHaArguments disableHaArguments) {
    return resource.path("commands").path("hdfsDisableHa").post(Command.class, disableHaArguments);
  }

  @Override
  public Command enableHdfsHa(HdfsHaArguments haArguments) {
    return resource.path("commands").path("hdfsEnableHa").post(Command.class, haArguments);
  }

  @Override
  public Command hdfsFailover(RoleNameList names, boolean forceFailover) {
    return resource.path("commands").path("hdfsFailover")
        .queryParam("force", Boolean.toString(forceFailover))
        .post(Command.class, names);
  }

  @Override
  public Command hueCreateHiveWarehouse() {
    return resource.path("commands").path("hueCreateHiveWarehouse").post(Command.class);
  }

  @Override
  public Command restart() {
    return resource.path("commands").path("restart").post(Command.class);
  }

  @Override
  public Command start() {
    return resource.path("commands").path("start").post(Command.class);
  }

  @Override
  public Command stop() {
    return resource.path("commands").path("stop").post(Command.class);
  }

  @Override
  public Command cleanUpZookeeper() {
    return resource.path("commands").path("zooKeeperCleanup").post(Command.class);
  }

  @Override
  public Command initZooKeeper() {
    return resource.path("commands").path("zooKeeperInit").post(Command.class);
  }

  @Override
  public ServiceConfig getConfig() {
    return resource.path("config").get(ServiceConfig.class);
  }

  @Override
  public ServiceConfig getConfig(boolean fullView) {
    if (fullView) {
      return resource.path("config").queryParam("view", "full").get(ServiceConfig.class);
    }
    return resource.path("config").get(ServiceConfig.class);
  }

  @Override
  public ServiceConfig updateConfig(ServiceConfig newConfig) {
    return resource.path("config").put(ServiceConfig.class, newConfig);
  }

  @Override
  public MetricList getMetrics(long from, long to, String metrics) {
    return resource.path("metrics")
        .queryParam("from", Long.toString(from))
        .queryParam("to", Long.toString(to))
        .queryParam("metrics", metrics)
        .get(MetricList.class);
  }

  @Override
  public NameServiceList getNameServices() {
    return resource.path("nameservices").get(NameServiceList.class);
  }

  @Override
  public NameService getNameServiceDetails(String nameService) {
    return resource.path("nameservice").path(nameService).get(NameService.class);
  }

  @Override
  public MetricList getNameServiceMetrics(String nameservice, String metrics, long from, long to) {
    return resource.path(nameservice).path("metrics")
        .queryParam("metrics", metrics)
        .queryParam("from", Long.toString(from))
        .queryParam("to", Long.toString(to))
        .get(MetricList.class);
  }

  @Override
  public BulkCommandList bootstrapHdfsStandby(RoleNameList standByNodes) {
    return resource.path("roleCommands").path("hdfsBootstrapStandBy").post(BulkCommandList.class, standByNodes);
  }

  @Override
  public BulkCommandList hdfsFormat(RoleNameList nameNodesToFormat) {
    return resource.path("roleCommands").path("hdfsFormat").post(BulkCommandList.class, nameNodesToFormat);
  }

  @Override
  public BulkCommandList hdfsInitializeAutoFailover(RoleNameList controllersToInitialize) {
    return resource.path("roleCommands").path("hdfsInitializaAutoFailover")
        .post(BulkCommandList.class, controllersToInitialize);
  }

  @Override
  public BulkCommandList hdfsInitializeSharedDir(RoleNameList roleNames) {
    return resource.path("roleCommands").path("hdfsInitializeSharedDir").post(BulkCommandList.class, roleNames);
  }

  @Override
  public BulkCommandList hueSyncDb(RoleNameList hueServerRoles) {
    return resource.path("roleCommands").path("hueSyncDb").post(BulkCommandList.class, hueServerRoles);
  }

  @Override
  public BulkCommandList refresh(RoleNameList rolesNames) {
    return resource.path("roleCommands").path("refresh").post(BulkCommandList.class, rolesNames);
  }

  @Override
  public BulkCommandList restart(RoleNameList roleNames) {
    return resource.path("roleCommands").path("restart").post(BulkCommandList.class, roleNames);
  }

  @Override
  public BulkCommandList start(RoleNameList roleNames) {
    return resource.path("roleCommands").path("start").post(BulkCommandList.class, roleNames);
  }

  @Override
  public BulkCommandList stop(RoleNameList roleNames) {
    return resource.path("roleCommands").path("stop").post(BulkCommandList.class, roleNames);
  }

  @Override
  public BulkCommandList zooKeeperCleanUp(RoleNameList roleNames) {
    return resource.path("roleCommands").path("zooKeeperCleanup").post(BulkCommandList.class, roleNames);
  }

  @Override
  public BulkCommandList zooKeeperInit(RoleNameList roleNames) {
    return resource.path("roleCommands").path("zooKeeperInit").post(BulkCommandList.class, roleNames);
  }

  @Override
  public RoleList getRoles() {
    return resource.path("roles").get(RoleList.class);
  }

  @Override
  public RoleList createRoles(RoleList newRoles) {
    return resource.path("roles").post(RoleList.class, newRoles);
  }

  @Override
  public Role getRoleDetails(String roleName) {
    return resource.path("roles").path(roleName).get(Role.class);
  }

  @Override
  public Role deleteRole(String roleName) {
    return resource.path("roles").path(roleName).delete(Role.class);
  }

  @Override
  public CommandList getActiveRoleCommands(String roleName) {
    return resource.path("roles").path(roleName).path("commands").get(CommandList.class);
  }

  @Override
  public ConfigList getRoleConfig(String roleName) {
    return resource.path("roles").path(roleName).path("config").get(ConfigList.class);
  }

  @Override
  public ConfigList updateRoleConfig(String roleName, ConfigList newConfig, String message) {
    if (message != null) {
      return resource.path("roles").path(roleName).path("config")
          .queryParam("message", message)
          .put(ConfigList.class, newConfig);
    } else {
      return resource.path("roles").path(roleName).path("config")
          .put(ConfigList.class, newConfig);
    }
  }

  @Override
  public ConfigList updateRoleConfig(String roleName, ConfigList newConfig) {
    return updateRoleConfig(roleName, newConfig, null);
  }

  @Override
  public String getRoleFullLog(String roleName) {
    return resource.path("roles").path(roleName).path("logs").path("full").get(String.class);
  }

  @Override
  public String getRoleStandrdError(String roleName) {
    return resource.path("roles").path(roleName).path("logs").path("stderr").get(String.class);
  }

  @Override
  public String getRoleStandardOut(String roleName) {
    return resource.path("roles").path(roleName).path("logs").path("stdout").get(String.class);
  }

  @Override
  public MetricList getRoleMetrics(String roleName, String metrics, long from, long to) {
    return resource.path("roles").path(roleName).path("logs").path("metrics")
        .queryParam("metrics", metrics)
        .queryParam("from", Long.toString(from))
        .queryParam("to", Long.toString(to))
        .get(MetricList.class);
  }
}
