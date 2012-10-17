/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.ClusterAPI;
import com.axemblr.service.cm.apis.Clusters;
import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterList;
import com.sun.jersey.api.client.WebResource;

import java.util.Set;

/**
 * Cloudera Manager /clusters API implementation.
 */
public class DefaultClusters implements Clusters {

  private final String mountPoint;
  private final WebResource resource;

  public DefaultClusters(WebResource resource) {
    this("/api/v1/clusters", resource);
  }

  public DefaultClusters(String mountPoint, WebResource resource) {
    this.mountPoint = mountPoint;
    this.resource = resource.path(mountPoint);
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public ClusterList createClusters(Set<Cluster> clustersToCreate) {
    return resource.post(ClusterList.class, new ClusterList(clustersToCreate));
  }

  @Override
  public ClusterList getAllClusters() {
    return resource.get(ClusterList.class);
  }

  @Override
  public ClusterAPI getCluster(String clusterName) {
    return new DefaultClusterAPI(resource, clusterName);
  }

  @Override
  public Cluster getClusterDetails(String clusterName) {
    return resource.path(clusterName).get(Cluster.class);
  }

  @Override
  public Cluster deleteCluster(String clusterName) {
    return resource.path(clusterName).delete(Cluster.class);
  }
}
