/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.ClouderaManagementCommands;
import com.axemblr.service.cm.apis.ClouderaManagementService;
import com.axemblr.service.cm.apis.ClouderaManager;
import com.axemblr.service.cm.models.cm.CmVersionInfo;
import com.axemblr.service.cm.models.cm.CollectDiagnosticDataArguments;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.License;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

import java.util.List;

/**
 * Cloudera Manager /cm API implementation
 */
public class DefaultClouderaManager implements ClouderaManager, ClouderaManagementCommands {

  private final String mountPoint;
  private final WebResource resource;

  public DefaultClouderaManager(WebResource resource) {
    this("/api/v1/cm", resource);
  }

  public DefaultClouderaManager(String mountPoint, WebResource resource) {
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
  public CmVersionInfo getVersionInfo() {
    return resource.path("version").get(CmVersionInfo.class);
  }

  @Override
  public String getLogFile() {
    return resource.path("log").get(String.class);
  }

  @Override
  public License getLicense() {
    return resource.path("license").get(License.class);
  }

  @Override
  public License updateLicense(String multipartLicense) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public ConfigList getConfiguration() {
    return resource.path("config").get(ConfigList.class);
  }

  @Override
  public ConfigList updateConfig(List<Config> newConfig) {
    return resource.path("config").put(ConfigList.class, new ConfigList(newConfig));
  }

  @Override
  public ClouderaManagementService getService() {
    return new DefaultClouderaManagementService(resource);
  }


  @Override
  public CommandList getActiveCommands() {
    return resource.path("commands").get(CommandList.class);
  }

  @Override
  public Command collectDiagnosticData(CollectDiagnosticDataArguments args) {
    return resource.path("commands")
        .path("collectDiagnosticData")
        .post(Command.class);
  }

  @Override
  public Command generateCredentials() {
    return resource.path("commands")
        .path("generateCredentials").post(Command.class);
  }

  @Override
  public Command inspectHosts() {
    return resource.path("commands")
        .path("inspectHosts")
        .post(Command.class);
  }
}
