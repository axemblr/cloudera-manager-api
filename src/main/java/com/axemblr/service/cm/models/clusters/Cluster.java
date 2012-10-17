/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A cluster represents a set of interdependent services running on a set of hosts. All services on a given
 * cluster are of the same software version (e.g. CDH3 or CDH4).
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_cluster.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiCluster.html
 */
public class Cluster {
  private final String name;
  private final ClusterVersion version;

  @JsonCreator
  public Cluster(@JsonProperty("name") String name,
                 @JsonProperty("version") ClusterVersion version) {
    this.name = checkNotNull(name);
    this.version = checkNotNull(version);
  }

  /**
   * The name of the cluster.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The CDH version of the cluster.
   *
   * @return
   */
  public ClusterVersion getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Cluster)) return false;

    Cluster cluster = (Cluster) o;

    if (name != null ? !name.equals(cluster.name) : cluster.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ClusterAPI{" +
        "name='" + name + '\'' +
        ", version=" + version +
        '}';
  }
}
