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
 * A list of role names.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_roleNames.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleNameList.html
 */
public class RoleNameList {
  private final ImmutableSet<String> items;

  public RoleNameList(String... roles) {
    this(ImmutableSet.copyOf(roles));
  }

  @JsonCreator
  public RoleNameList(@JsonProperty("items") Set<String> items) {
    this.items = (items == null) ? ImmutableSet.<String>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<String> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleNameList)) return false;

    RoleNameList that = (RoleNameList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "RoleNameList{" +
        "items=" + items +
        '}';
  }
}
