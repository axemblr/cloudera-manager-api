/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.Hosts;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.hosts.Host;
import com.axemblr.service.cm.models.hosts.HostList;
import com.axemblr.service.cm.models.hosts.MetricList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

import java.util.List;
import java.util.Set;

/**
 * Cloudera Manager Hosts API implementation.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-_config.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-_metrics.html
 */
public class DefaultHosts implements Hosts {

  private final String mountPoint;
  private final WebResource resource;

  public DefaultHosts(WebResource resource) {
    this("/api/v1/hosts", resource);
  }

  public DefaultHosts(String mountPoint, WebResource resource) {
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
  public HostList createHosts(Set<Host> hostsToCreate) {
    return resource.post(HostList.class, new HostList(hostsToCreate));
  }

  @Override
  public HostList list() {
    return resource.get(HostList.class);
  }

  @Override
  public HostList deleteAll() {
    return resource.delete(HostList.class);
  }

  @Override
  public Host getHostById(String hostId) {
    return resource.path(hostId).get(Host.class);
  }

  @Override
  public Host updateHost(String hostId, Host updatedHost) {
    return resource.path(checkNotNull(hostId)).put(Host.class, checkNotNull(updatedHost));
  }

  @Override
  public Host removeHostById(String hostId) {
    return resource.path(hostId).delete(Host.class);
  }

  @Override
  public Host removeHost(Host host) {
    return removeHostById(checkNotNull(host).getHostId());
  }

  @Override
  public ConfigList getHostConfig(String hostId) {
    return resource.path(hostId).path("config").get(ConfigList.class);
  }

  @Override
  public ConfigList getHostConfig(Host host) {
    return getHostConfig(checkNotNull(host).getHostId());
  }

  @Override
  public ConfigList updateHostConfig(String hostId, List<Config> newConfig) {
    return resource.path(hostId).path("config").put(ConfigList.class, new ConfigList(newConfig));
  }

  @Override
  public ConfigList updateHostConfig(Host host, List<Config> newConfig) {
    return updateHostConfig(checkNotNull(host).getHostId(), newConfig);
  }

  @Override
  public ConfigList updateHostConfig(String hostId, List<Config> newConfig, String message) {
    if (message != null) {
      return resource.path(hostId).path("config")
          .queryParam("message", message)
          .put(ConfigList.class, new ConfigList(newConfig));
    } else {
      return updateHostConfig(hostId, newConfig);
    }
  }

  @Override
  public MetricList getHostMetrics(String hostId, String startDate, String endDate, boolean queryNetwork, String interfaces, boolean queryStorage, String storageIds, String metrics) {
    throw new UnsupportedOperationException("Not yet supported");
  }
}
