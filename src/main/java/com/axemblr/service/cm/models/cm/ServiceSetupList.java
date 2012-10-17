/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A list of service setup objects.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_serviceSetupList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiServiceSetupList.html
 */
public class ServiceSetupList {
  private final ImmutableSet<ServiceSetupInfo> items;

  public ServiceSetupList(ServiceSetupInfo serviceSetupInfo) {
    this(Sets.newHashSet(serviceSetupInfo));
  }

  @JsonCreator
  public ServiceSetupList(@JsonProperty("items") Set<ServiceSetupInfo> items) {
    this.items = (items == null) ? ImmutableSet.<ServiceSetupInfo>of() : ImmutableSet.copyOf(items);
  }

  /**
   * @return array of items/service
   */
  public ImmutableSet<ServiceSetupInfo> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceSetupList)) return false;

    ServiceSetupList that = (ServiceSetupList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ServiceSetupList{" +
        "items=" + items +
        '}';
  }
}
