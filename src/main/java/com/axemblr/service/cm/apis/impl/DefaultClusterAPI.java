/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.ClusterAPI;
import com.axemblr.service.cm.apis.ServiceAPI;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceSetupList;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

/**
 * Cloudera Manager /clusters/{cluster} API implementation.
 */
public class DefaultClusterAPI implements ClusterAPI {

  private final String clusterName;
  private final WebResource resource;

  public DefaultClusterAPI(WebResource resource, String clusterName) {
    this.clusterName = checkNotNull(clusterName);
    this.resource = resource.path(clusterName);
  }


  @Override
  public CommandList getActiveCommands() {
    return resource.path("commands").get(CommandList.class);
  }

  @Override
  public Command restartServices() {
    return resource.path("commands").path("restart").post(Command.class);
  }

  @Override
  public Command startServices() {
    return resource.path("commands").path("start").post(Command.class);
  }

  @Override
  public Command stopServices() {
    return resource.path("commands").path("stop").post(Command.class);
  }

  @Override
  public Command upgradeServicesToCDH4() {
    return resource.path("commands").path("upgradeServices").post(Command.class);
  }

  @Override
  public ServiceList getServices() {
    return resource.path("services").get(ServiceList.class);
  }

  @Override
  public ServiceList registerServices(ServiceSetupList servicesToCreate) {
    return resource.path("services").post(ServiceList.class, servicesToCreate);
  }

  @Override
  public Service getServiceDetails(String serviceName) {
    return resource.path("services").path(serviceName).get(Service.class);
  }

  @Override
  public Service deleteService(String serviceName) {
    return resource.path("services").path(serviceName).delete(Service.class);
  }

  @Override
  public ServiceAPI getService(String serviceName) {
    return new DefaultServiceAPI(resource, serviceName);
  }
}
