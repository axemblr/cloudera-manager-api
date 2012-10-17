/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.clusters.Cluster;
import com.axemblr.service.cm.models.clusters.ClusterList;

import java.util.Set;

/**
 * Cloudera Manager /clusters API.
 * <p/>
 * A cluster is a set of hosts running interdependent services. All services in a cluster have the same CDH version.
 * A Cloudera Manager installation may have multiple clusters, which are uniquely identified by different names.
 * <p/>
 * You can issue commands against a cluster.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public interface Clusters {

  /**
   * Creates a collection of clusters.
   *
   * @param clustersToCreate List of clusters to created.
   * @return List of created clusters.
   */
  public ClusterList createClusters(Set<Cluster> clustersToCreate);

  /**
   * Lists all known clusters.
   *
   * @return List of known clusters.
   */
  public ClusterList getAllClusters();

  /**
   * Reads information about a cluster.
   *
   * @param clusterName
   * @return
   */
  public ClusterAPI getCluster(String clusterName);

  /**
   * Reads information about a cluster.
   *
   * @param clusterName Name of cluster to look up.
   * @return Details of requested cluster.
   */
  public Cluster getClusterDetails(String clusterName);

  /**
   * Deletes a cluster.
   *
   * @param clusterName Name of cluster to delete.
   * @return Details of deleted cluster.
   */
  public Cluster deleteCluster(String clusterName);

}
