/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.users.User;
import com.axemblr.service.cm.models.users.UserList;

import java.util.Set;

/**
 * Users API operations for Cloudera Manager.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__users.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__users_-userName-.html
 */
public interface Users {

  public static final String BASE_PATH = "/users";

  /**
   * Creates a list of users.When creating new users, the password property of each user should be their
   * plain text password. The returned user information will not contain any password information.
   *
   * @param usersToCreate A list of users to create.
   * @return Information about created users.
   */
  public UserList createUsers(Set<User> usersToCreate);

  /**
   * Returns a list of the user names configured in the system.
   *
   * @return A list of users.
   */
  public UserList list();

  /**
   * Deletes a user from the system.
   *
   * @param user User to delete.
   * @return The deleted user's information.
   */
  public User delete(String user);

  /**
   * Deletes a user from the system.
   *
   * @param user User to delete.
   * @return The deleted user's information.
   */
  public User delete(User user);

  /**
   * Returns detailed information about a user.
   *
   * @param user The user's name.
   * @return
   */
  public User details(String user);

  /**
   * Updates the given user's information. Note that the user's name cannot be changed.
   *
   * @param updatedInfo The user information.
   * @return updated user info;
   */
  public User update(User updatedInfo);

}
