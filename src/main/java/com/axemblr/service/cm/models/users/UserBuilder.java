/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.users;

import com.google.common.collect.ImmutableSet;

public class UserBuilder {
  private String name;
  private String password;
  private ImmutableSet<String> roles;

  public UserBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder setRoles(ImmutableSet<String> roles) {
    this.roles = roles;
    return this;
  }

  public User createUser() {
    return new User(name, password, roles);
  }
}