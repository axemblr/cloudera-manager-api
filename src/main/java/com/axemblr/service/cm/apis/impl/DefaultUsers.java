/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.Users;
import com.axemblr.service.cm.models.users.User;
import com.axemblr.service.cm.models.users.UserList;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * Default implementation for User API.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__users_-userName-.html
 */
public class DefaultUsers implements Users {

  private final String mountPoint;
  private final WebResource resource;


  public DefaultUsers(WebResource resource) {
    this("/api/v1/users", resource);
  }

  public DefaultUsers(String mountPoint, WebResource origin) {
    this.mountPoint = checkNotNull(mountPoint);
    this.resource = checkNotNull(origin).path(mountPoint);
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public UserList createUsers(Set<User> usersToCreate) {
    return resource.type(MediaType.APPLICATION_JSON_TYPE)
        .post(UserList.class, new UserList(usersToCreate));
  }

  @Override
  public UserList list() {
    return resource.get(UserList.class);
  }

  @Override
  public User delete(String user) {
    return resource.path(user).delete(User.class);
  }

  @Override
  public User delete(User user) {
    return delete(user.getName());
  }

  @Override
  public User details(String user) {
    return resource.path(user).get(User.class);
  }

  @Override
  public User update(User updatedInfo) {
    return resource.path(updatedInfo.getName()).put(User.class, User.class);
  }
}
