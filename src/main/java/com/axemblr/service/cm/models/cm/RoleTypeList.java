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
 * A list of roles types that exists for a given service.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_roleTypeList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleTypeList.html
 */
public class RoleTypeList {
  private final ImmutableSet<RoleType> items;

  public RoleTypeList(RoleType... items) {
    this(ImmutableSet.copyOf(items));
  }

  @JsonCreator
  public RoleTypeList(@JsonProperty("items") Set<RoleType> items) {
    this.items = (items == null) ? ImmutableSet.<RoleType>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<RoleType> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleTypeList)) return false;

    RoleTypeList that = (RoleTypeList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "RoleTypeList{" +
        "items=" + items +
        '}';
  }
}
