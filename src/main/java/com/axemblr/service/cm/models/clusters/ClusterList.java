/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A list of clusters.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_clusterList.html
 */
public class ClusterList {

  private final ImmutableSet<Cluster> items;

  @JsonCreator
  public ClusterList(@JsonProperty("items") Set<Cluster> items) {
    this.items = (items == null) ? ImmutableSet.<Cluster>of() : ImmutableSet.copyOf(items);
  }

  /**
   * A list of clusters.
   *
   * @return
   */
  public ImmutableSet<Cluster> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClusterList)) return false;

    ClusterList that = (ClusterList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ClusterList{" +
        "items=" + items +
        '}';
  }
}
