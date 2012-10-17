/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.users;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A list of users.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_userList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiUserList.html
 */
public class UserList {
  private final ImmutableSet<User> items;

  @JsonCreator
  public UserList(@JsonProperty("items") Set<User> items) {
    this.items = (items == null) ? ImmutableSet.<User>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<User> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "UserList{" +
        "items=" + items +
        '}';
  }
}
