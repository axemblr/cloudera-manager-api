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
 * A list of roles.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_roleList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleList.html
 */
public class RoleList {
  private final ImmutableSet<Role> items;

  @JsonCreator
  public RoleList(@JsonProperty("items") Set<Role> items) {
    this.items = (items == null) ? ImmutableSet.<Role>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<Role> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleList)) return false;

    RoleList roleList = (RoleList) o;

    if (items != null ? !items.equals(roleList.items) : roleList.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "RoleList{" +
        "items=" + items +
        '}';
  }
}
