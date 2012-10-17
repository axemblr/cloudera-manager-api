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

/**
 * This is the model for user information in the Cloudera Manager API.
 * <p/>
 * Note that any method that returns user information will not contain any password information.
 * The password property is only used when creating or updating users.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_user.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiUser.html
 */
public class User {

  private final String name;
  private final String password;
  private final ImmutableSet<String> roles;

  @JsonCreator
  public User(@JsonProperty("name") String name,
              @JsonProperty("password") String password,
              @JsonProperty("roles") ImmutableSet<String> roles) {
    this.name = name;
    this.password = password;
    this.roles = roles;
  }

  /**
   * The username, which is unique within a Cloudera Manager installation.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the user password. Passwords are not returned when querying user information, so this property will
   * always be empty when reading information from a server.
   *
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   * A list of roles this user belongs to. Current possible values are: "ROLE_ADMIN" or empty.
   *
   * @return
   */
  public ImmutableSet<String> getRoles() {
    return roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;

    User user = (User) o;

    if (name != null ? !name.equals(user.name) : user.name != null) return false;
    if (password != null ? !password.equals(user.password) : user.password != null) return false;
    if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    return result;
  }
}
