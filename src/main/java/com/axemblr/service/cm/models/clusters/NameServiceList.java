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
 * A list of HDFS nameservices.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_nameserviceList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiNameserviceList.html
 */
public class NameServiceList {
  private final ImmutableSet<NameService> items;

  @JsonCreator
  public NameServiceList(@JsonProperty("items") Set<NameService> items) {
    this.items = (items == null) ? ImmutableSet.<NameService>of() : ImmutableSet.copyOf(items);
  }

  /**
   * Set of nameservices.
   *
   * @return
   */
  public ImmutableSet<NameService> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NameServiceList)) return false;

    NameServiceList that = (NameServiceList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "NameServiceList{" +
        "items=" + items +
        '}';
  }
}
