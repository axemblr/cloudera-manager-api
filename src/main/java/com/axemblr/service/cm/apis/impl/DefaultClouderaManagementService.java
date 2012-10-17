/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.ClouderaManagementService;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.cm.RoleTypeList;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceSetupInfo;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

/**
 * Cloudera Manager /cm/service/ API implementation.
 */
public class DefaultClouderaManagementService implements ClouderaManagementService {

  private final String mountPoint;
  private final WebResource resource;

  public DefaultClouderaManagementService(WebResource resource) {
    this("service", resource);
  }

  public DefaultClouderaManagementService(String mountPoint, WebResource resource) {
    this.mountPoint = checkNotNull(mountPoint);
    this.resource = checkNotNull(resource).path(mountPoint);
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public Service getManagementServiceInfo() {
    return resource.get(Service.class);
  }

  @Override
  public Service setupManagementService(ServiceSetupInfo setupInfo) {
    return resource.put(ServiceSetupInfo.class, setupInfo);
  }

  @Override
  public CommandList listActiveCommands() {
    return resource.path("commands").get(CommandList.class);
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
  public ServiceConfig getConfig() {
    return resource.path("config").get(ServiceConfig.class);
  }

  @Override
  public ServiceConfig updateConfig(ServiceConfig newServiceConfig) {
    return resource.path("config").put(ServiceConfig.class, newServiceConfig);
  }

  @Override
  public ServiceConfig updateConfig(ServiceConfig newServiceConfig, String message) {
    if (message == null) {
      return updateConfig(newServiceConfig);
    } else {
      return resource.path("config").queryParam("message", message).put(ServiceConfig.class, newServiceConfig);
    }
  }

  @Override
  public BulkCommandList restartRoles(RoleNameList rolesToRestart) {
    return resource.path("roleCommands").path("restart").post(BulkCommandList.class, rolesToRestart);
  }

  @Override
  public BulkCommandList startRoles(RoleNameList roleNamesToStart) {
    return resource.path("roleCommands").path("start").post(BulkCommandList.class, roleNamesToStart);
  }

  @Override
  public BulkCommandList stopRoles(RoleNameList rolesToStop) {
    return resource.path("roleCommands").path("stop").post(BulkCommandList.class, rolesToStop);
  }

  @Override
  public RoleList createRoles(RoleList rolesToCreate) {
    return resource.path("roles").post(RoleList.class, rolesToCreate);
  }

  @Override
  public RoleList listRoles() {
    return resource.path("roles").get(RoleList.class);
  }

  @Override
  public Role deleteRole(String role) {
    return resource.path("roles").path(role).delete(Role.class);
  }

  @Override
  public Role deleteRole(Role role) {
    return deleteRole(role.getName());
  }

  @Override
  public Role getRole(String role) {
    return resource.path("roles").path(role).get(Role.class);
  }

  @Override
  public CommandList getActiveRoleCommands(String role) {
    return resource.path("roles").path(role).path("commands").get(CommandList.class);
  }

  @Override
  public ConfigList getRoleConfig(String role) {
    return resource.path("roles").path(role).path("config").get(ConfigList.class);
  }

  @Override
  public ConfigList updateRoleConfig(String role, ConfigList newRoleConfig, String message) {
    if (message == null) {
      return resource.path("roles").path(role).path("config").put(ConfigList.class, newRoleConfig);
    } else {
      return resource.path("roles").path(role).path("config").queryParam("message", message)
          .put(ConfigList.class, newRoleConfig);
    }
  }

  @Override
  public RoleTypeList getRoleTypes() {
    return resource.path("roleTypes").get(RoleTypeList.class);
  }
}
