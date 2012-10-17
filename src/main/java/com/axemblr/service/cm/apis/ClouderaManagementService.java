/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.cm.RoleTypeList;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceSetupInfo;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;

/**
 * Cloudera Manager /cm/service/ API.
 * <p/>
 * Only available in the Enterprise Edition, the Management Service provides monitoring, diagnostic and reporting
 * features for your Hadoop clusters. The operation of this service is similar to other Hadoop services, except that
 * the Management Service does not belong to a cluster.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public interface ClouderaManagementService {

  /**
   * Retrieve information about the Cloudera Management Services.
   *
   * @return Details about the management service.
   */
  public Service getManagementServiceInfo();

  /**
   * Setup the Cloudera Management Services. Configure the CMS instance and create all the management roles.
   * The provided configuration data can be used to set up host mappings for each role, and required configuration
   * such as database connection information for specific roles. Regardless of the list of roles provided in the
   * input data, all management roles are created by this call. The input is used to override any default settings
   * for the specific roles. This method needs a valid CM license to be installed beforehand.
   * This method does not start any services or roles. This method will fail if a CMS instance already exists.
   * <p/>
   * Available role types:
   * <ul>
   * <li>SERVICEMONITOR</li>
   * <li>ACTIVITYMONITOR</li>
   * <li>HOSTMONITOR</li>
   * <li>REPORTSMANAGER</li>
   * <li>EVENTSERVER</li>
   * <li>ALERTPUBLISHER</li>
   * </ul>
   *
   * @param setupInfo Role configurartion overrides.
   * @return ApiService The CMS information.
   */
  public Service setupManagementService(ServiceSetupInfo setupInfo);

  /**
   * List active Cloudera Management Services commands.
   *
   * @return A list of active role commands.
   */
  public CommandList listActiveCommands();

  /**
   * Restart the Cloudera Management Services.
   *
   * @return A reference to the submitted command.
   */
  public Command restart();

  /**
   * Start the Cloudera Management Services.
   *
   * @return A reference to the submitted command.
   */
  public Command start();

  /**
   * Stop the Cloudera Management Services.
   *
   * @return A reference to the submitted command.
   */
  public Command stop();

  /**
   * Retrieve the configuration of the Cloudera Management Services.
   *
   * @return List with configured and available configuration options.
   */
  public ServiceConfig getConfig();

  /**
   * Update the Cloudera Management Services configuration. If a value is set in the given configuration, it will
   * be added to the service's configuration, replacing any existing entries. If a value is unset (its value is null),
   * the existing configuration for the attribute will be erased, if any.
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param newServiceConfig Configuration changes.
   * @return The new service configuration.
   */
  public ServiceConfig updateConfig(ServiceConfig newServiceConfig);

  /**
   * Update the Cloudera Management Services configuration. If a value is set in the given configuration, it will
   * be added to the service's configuration, replacing any existing entries. If a value is unset (its value is null),
   * the existing configuration for the attribute will be erased, if any.
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param newServiceConfig Configuration changes.
   * @param message          Optional message describing changes.
   * @return The new service configuration.
   */
  public ServiceConfig updateConfig(ServiceConfig newServiceConfig, String message);

  /**
   * Restart a set of Cloudera Management Services roles.
   *
   * @param rolesToRestart The roles to restart.
   * @return A list of submitted commands (summary view).
   */
  public BulkCommandList restartRoles(RoleNameList rolesToRestart);

  /**
   * Start a set of Cloudera Management Services roles.
   *
   * @param rolesToStart The roles to start.
   * @return A list of submitted commands (summary view).
   */
  public BulkCommandList startRoles(RoleNameList rolesToStart);

  /**
   * Stop a set of Cloudera Management Services roles.
   *
   * @param rolesToStop The roles to stop.
   * @return A list of submitted commands (summary view).
   */
  public BulkCommandList stopRoles(RoleNameList rolesToStop);

  /**
   * Create new roles in the Cloudera Management Services.
   *
   * @param rolesToCreate Roles to create.
   * @return List of created roles.
   */
  public RoleList createRoles(RoleList rolesToCreate);

  /**
   * List all roles of the Cloudera Management Services.
   *
   * @return List of roles.
   */
  public RoleList listRoles();

  /**
   * Delete a role from the Cloudera Management Services.
   *
   * @param role The role name.
   * @return The details of the deleted role.
   */
  public Role deleteRole(String role);

  /**
   * Delete a role from the Cloudera Management Services.
   *
   * @param role The role name.
   * @return The details of the deleted role.
   */
  public Role deleteRole(Role role);

  /**
   * Retrieve detailed information about a Cloudera Management Services role.
   *
   * @param role The role name.
   * @return The details of the deleted role.
   */
  public Role getRole(String role);

  /**
   * List active role commands.
   *
   * @param role The role name.
   * @return A list of active role commands.
   */
  public CommandList getActiveRoleCommands(String role);

  /**
   * Retrieve the configuration of a specific Cloudera Management Services role.
   *
   * @param role The role to look up.
   * @return List with configured and available configuration options.
   */
  public ConfigList getRoleConfig(String role);

  /**
   * Update the configuration of a Cloudera Management Services role. If a value is set in the given configuration,
   * it will be added to the role's configuration, replacing any existing entries. If a value is unset
   * (its value is null), the existing configuration for the attribute will be erased, if any.
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param role          The role to modify.
   * @param newRoleConfig Configuration changes.
   * @param message       Optional message describing the changes.
   * @return The new service configuration.
   */
  public ConfigList updateRoleConfig(String role, ConfigList newRoleConfig, String message);

  /**
   * List the supported role types for the Cloudera Management Services.
   *
   * @return List of role types the service supports.
   */
  public RoleTypeList getRoleTypes();

}
