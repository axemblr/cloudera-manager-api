/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceSetupList;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;

/**
 * Cloudera Manager /clusters/{cluster} API.
 * All commands are relative to a specific cluster.
 */
public interface ClusterAPI {

  /**
   * List active cluster commands.
   *
   * @return A list of active cluster commands.
   */
  public CommandList getActiveCommands();

  /**
   * Restart all services in the cluster. Services are restarted in the appropriate order given their dependencies.
   *
   * @return Information about the submitted command.
   */
  public Command restartServices();

  /**
   * Start all services in the cluster. Services are started in the appropriate order given their dependencies.
   *
   * @return Information about the submitted command.
   */
  public Command startServices();

  /**
   * Stop all services in the cluster. Services are stopped in the appropriate order given their dependencies.
   *
   * @return Information about the submitted command.
   */
  public Command stopServices();

  /**
   * Upgrades the services in the cluster to the CDH4 version. This command requires that all services in the cluster
   * are stopped, and that the CDH packages in the hosts used by the cluster be upgraded to CDH4 before
   * the command is issued.
   * The command will upgrade the services and their configuration to the version available in the CDH4 distribution.
   *
   * @return Information about the submitted command.
   */
  public Command upgradeServicesToCDH4();

  /**
   * Lists all services registered in the cluster.
   *
   * @return List of services.
   */
  public ServiceList getServices();

  /**
   * Creates a list of services. <br/>
   * There are typically two service creation strategies: <br/>
   * <ol>
   * <li>The caller may choose to set up a new service piecemeal, by first creating the service itself
   * (without any roles or configuration), and then create the roles, and then specify configuration.</li>
   * <li>Alternatively, the caller can pack all the information in one call, by fully specifying the fields
   * in the ApiServiceSetupInfo object,
   * <ul>
   * <li>with service config and role type config, and</li>
   * <li>role to host assignment.</li>
   * </ul>
   * </li>
   * </ol>
   * Available service types for each cluster version:
   * <ul>
   * <li><strong>CDH3</strong>:	HDFS, MAPREDUCE, HBASE, OOZIE, ZOOKEEPER, HUE</li>
   * <li><strong>CDH4</strong>: HDFS, MAPREDUCE, HBASE, OOZIE, ZOOKEEPER, HUE, YARN</li>
   * </ul>
   *
   * @param servicesToCreate Details of the services to create.
   * @return List of created services.
   */
  public ServiceList registerServices(ServiceSetupList servicesToCreate);

  /**
   * Retrieves details information about a service.
   *
   * @param serviceName The service name.
   * @return The details of the service.
   */
  public Service getServiceDetails(String serviceName);

  /**
   * Deletes a service from the system.
   *
   * @param serviceName The name of the service to delete.
   * @return The details of the deleted service.
   */
  public Service deleteService(String serviceName);

  /**
   * API to manage Services for a cluster.
   *
   * @return
   */
  public ServiceAPI getService(String serviceName);

}
