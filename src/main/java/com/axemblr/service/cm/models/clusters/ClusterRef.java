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
 * A clusterRef references a cluster. To operate on the cluster object, use the cluster API with the
 * clusterName as the parameter.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_clusterRef.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiClusterRef.html
 */
public class ClusterRef {

  private final String clusterName;

  @JsonCreator
  public ClusterRef(@JsonProperty("clusterName") String clusterName) {
    this.clusterName = checkNotNull(clusterName);
  }

  /**
   * The name of the cluster, which uniquely identifies it in a CM installation.
   */
  public String getClusterName() {
    return clusterName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClusterRef)) return false;

    ClusterRef that = (ClusterRef) o;

    if (clusterName != null ? !clusterName.equals(that.clusterName) : that.clusterName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return clusterName != null ? clusterName.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ClusterRef{" +
        "clusterName='" + clusterName + '\'' +
        '}';
  }
}
