/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A list of services.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_serviceList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiServiceList.html
 */
public class ServiceList {
  private final ImmutableSet<Service> items;


  @JsonCreator
  public ServiceList(@JsonProperty("items") Set<Service> items) {
    this.items = (items == null) ? ImmutableSet.<Service>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<Service> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceList)) return false;

    ServiceList that = (ServiceList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ServiceList{" +
        "items=" + items +
        '}';
  }
}
