/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.clusters.ActivityList;
import com.axemblr.service.cm.models.clusters.HdfsDisableHaArguments;
import com.axemblr.service.cm.models.clusters.HdfsFailoverArguments;
import com.axemblr.service.cm.models.clusters.HdfsHaArguments;
import com.axemblr.service.cm.models.clusters.NameService;
import com.axemblr.service.cm.models.clusters.NameServiceList;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import com.axemblr.service.cm.models.hosts.MetricList;

/**
 * API for Cloudera Manager Cluster Services .
 * <p/>
 * A service is an abstract entity providing a capability in a cluster. Examples of services are HDFS, MapReduce, YARN,
 * and HBase. A service is usually distributed, and contains a set of roles that physically run on the cluster.
 * A service has its own configuration, status, metrics, and roles. You may issue commands against a service, or against
 * a set of roles in bulk. Additionally, an HDFS service has nameservices, and a MapReduce service has activities.
 * <p/>
 * All services belong to a cluster (except for the Cloudera Management Service), and is uniquely identified by its
 * name within a Cloudera Manager installation. The types of services available depends on the CDH version of the cluster.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public interface ServiceAPI {

  /**
   * Read all activities in the system.
   * <p/>
   * The query to perform to find activities in the system. By default, this call returns top level (i.e. root) a
   * ctivities that have currently started. The query specifies the intersection of a list of constraints,
   * joined together with semicolons (without spaces). <br/>
   * For example:
   * <p/>
   * <ul>
   * <li><strong>status==started;parent==</strong> looks for running root activities. This is also the default query.</li>
   * <li><strong>status==failed;finishTime=gt=2012-04-01T20:30:00.000Z</strong> looks for failed activities after
   * the given date time. </li>
   * <li><strong>name==Pi Estimator;startTime=gt=2012-04-01T20:30:00.000Z</strong> looks for activities started after
   * the given date time, with the name of "Pi Estimator". </li>
   * <li><strong> startTime=lt=2012-01-02T00:00:00.000Z;finishTime=ge=2012-01-01T00:00:00.000Z </strong> looks for
   * activities that are active on 2012 New Year's Day. Note that they may start before or finish after that day.</li>
   * <li><strong> status==failed;parent==000014-20120425161321-oozie-joe </strong> looks for failed child activities
   * of the given parent activity id.</li>
   * <li><strong>status==started;metrics.cpu_user=gt=10</strong> looks for started activities that are using more
   * than 10 cores per second. </li>
   * <li><strong>type==hive;metrics.user==bc;finishTime=gt=2012-04-01T20:30:00.000Z</strong> looks for all hive queries
   * submitted by user bc after the given date time.</li>
   * </ul>
   * You may query any fields present in the ApiActivity object. You can also query by activity metric values using
   * the metrics. *syntax. <br/>
   * Values for date time fields should be ISO8601 timestamps.
   * The valid comparators are ==, !=, =lt=, =le=, =ge=, and =gt=. They stand for "==", "!=", "<", "<=", ">=", ">" respectively.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_activities.html
   *
   * @param query        The query to perform
   * @param resultOffset Specified the offset of activities to return.
   * @param maxResults   The maximum number of activities to return.
   * @return A list of activities.
   */
  public ActivityList getAllActivities(String query, int resultOffset, int maxResults);

  /**
   * Returns the child activities.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_activities_-activityId-_children.html
   *
   * @param activityId   The id of the activity
   * @param maxResults   The maximum number of activities to return.
   * @param resultOffset Specified the offset of activities to return.
   * @return The list of child activities for the specified activity
   */
  public ActivityList getActivityChildren(String activityId, int maxResults, int resultOffset);

  /**
   * Fetch metric readings for a particular activity. <br/>
   * By default, this call will look up all metrics available for the activity. If only specific metrics are desired,
   * use the metrics parameter. <br/>
   * By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults
   * to the current server time). The from and to parameters can be used to control the window being queried.
   * A maximum window of 3 hours is enforced. <br/>
   * When requesting a "full" view, aside from the extended properties of the returned metric data, the collection will
   * also contain information about all metrics available for the activity, even if no readings are available in the
   * requested window. <br/>
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_activities_-activityId-_metrics.html
   *
   * @param activityId The name of the activity.
   * @param from       Start of the period to query.
   * @param to         End of the period to query.
   * @param query      Filter for which metrics to query.
   * @return List of readings from the monitors.
   */
  public MetricList getActivityMetrics(String activityId, long from, long to, String query);

  /**
   * Returns a list of similar activities
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_activities_-activityId-_similar.html
   *
   * @param activityId The id of the activity
   * @return The list of similar activities to the specified activity
   */
  public ActivityList getSimilarActivities(String activityId);

  /**
   * List active service commands.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands.html
   *
   * @return A list of active service commands.
   */
  public CommandList getActiveCommands();

  /**
   * Decommission roles of a service. For HBase services, the list should contain names of RegionServers to decommission.
   * For HDFS services, the list should contain names of DataNodes to decommission.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_decommission.html
   *
   * @param rolesToDecommission List of role names to decommision.
   * @return Information about the submitted command.
   */
  public Command decommissionRoles(RoleNameList rolesToDecommission);

  /**
   * Deploy a service's client configuration. The client configuration is deployed to the hosts where the given roles are running.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_deployClientConfig.html
   *
   * @param rolesToConfigure List of role names.
   * @return Information about the submitted command.
   */
  public Command deployClientConfig(RoleNameList rolesToConfigure);

  /**
   * Creates the root directory of an HBase service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hbaseCreateRoot.html
   *
   * @return Information about the submitted command.
   */
  public Command createHBaseRoot();

  /**
   * Disable auto-failover for a highly available HDFS nameservice. The command will modify the nameservice's NameNodes
   * configuration to disable automatic failover, and delete the existing failover controllers.
   * The ZooKeeper dependency of the service will not be removed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsDisableAutoFailover.html
   *
   * @return Information about the submitted command.
   */
  public Command disableHdfsAutoFailover();

  /**
   * Enable auto-failover for an HDFS nameservice. <br/>
   * This command requires that the nameservice exists, and HA has been configured for that nameservice. <br/>
   * The command will create the needed failover controllers, perform the needed initialization and configuration, and will
   * start the new roles. The existing NameNodes which are part of the nameservice will be re-started in the process.<br/>
   * This process may require changing the service's configuration, to add a dependency on the provided ZooKeeper service.
   * This will be done if such a dependency has not been configured yet, and will cause roles that are not affected by
   * this command to show an "outdated configuration" status.
   * <p/>
   * If a ZooKeeper dependency has already been set up by some other means, it does not need to be provided in the
   * command arguments.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsEnableAutoFailover.html
   *
   * @param failoverArguments Arguments for the command.
   * @return Information about the submitted command.
   */
  public Command enableHdfsAutoFailover(HdfsFailoverArguments failoverArguments);

  /**
   * Disable high availability (HA) for an HDFS NameNode. The NameNode to be kept must be running before HA can be disabled.
   * As part of disabling HA, any services that depend on the HDFS service being modified will be stopped.
   * The command arguments provide options to re-start these services and to re-deploy the client configurations for
   * services of the cluster after HA has been disabled.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsDisableHa.html
   *
   * @param disableHaArguments Arguments for the command.
   * @return Information about the submitted command.
   */
  public Command disableHdfsHa(HdfsDisableHaArguments disableHaArguments);

  /**
   * Enable high availability (HA) for an HDFS NameNode. <p/>
   * This command only applies to CDH4 HDFS services.<p/>
   * The command will set up the given "active" and "stand-by" NameNodes as an HA pair. Both nodes need to already exist.
   * <p/>
   * If there is a SecondaryNameNode associated with either given NameNode instance, it will be deleted. <p/>
   * Note that while the shared edits path may be different for both nodes, they need to point to the same underlying
   * storage (e.g., an NFS share).
   * As part of enabling HA, any services that depend on the HDFS service being modified will be stopped. The command
   * arguments provide options to re-start these services and to re-deploy the client configurations for services of
   * the cluster after HA has been enabled.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsEnableHa.html
   *
   * @param haArguments Arguments for the command.
   * @return Information about the submitted command.
   */
  public Command enableHdfsHa(HdfsHaArguments haArguments);

  /**
   * Initiate a failover in an HDFS HA NameNode pair.
   * <p/>
   * The arguments should contain the names of the two NameNodes in the HA pair. The first one should be the currently
   * active NameNode, the second one the NameNode to be made active.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hdfsFailover.html
   *
   * @param names         Names of the NameNodes in the HA pair.
   * @param forceFailover Whether to force failover.
   * @return Information about the submitted command.
   */
  public Command hdfsFailover(RoleNameList names, boolean forceFailover);

  /**
   * Create the Beeswax role's Hive warehouse directory, on Hue services.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_hueCreateHiveWarehouse.html
   *
   * @return Information about the submitted command.
   */
  public Command hueCreateHiveWarehouse();

  /**
   * Restart the service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_restart.html
   *
   * @return Information about the submitted command.
   */
  public Command restart();

  /**
   * Start the service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_start.html
   *
   * @return Information about the submitted command.
   */
  public Command start();

  /**
   * Stop the service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_stop.html
   *
   * @return Information about the submitted command.
   */
  public Command stop();

  /**
   * Clean up all running server instances of a ZooKeeper service.
   * <p/>
   * This command removes snapshots and transaction log files kept by ZooKeeper for backup purposes. Refer to the ZooKeeper documentation for more details.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_zooKeeperCleanup.html
   *
   * @return Information about the submitted command.
   */
  public Command cleanUpZookeeper();

  /**
   * Initializes all the server instances of a ZooKeeper service.
   * <p/>
   * This command applies to ZooKeeper services from CDH4. ZooKeeper server roles need to be initialized before they can be used.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_commands_zooKeeperInit.html
   *
   * @return Information about the submitted command.
   */
  public Command initZooKeeper();

  /**
   * Retrieves the configuration of a specific service.
   * <p/>
   * The "summary" view contains only the configured parameters, and configuration for role types that contain
   * configured parameters.
   * <p/>
   * The "full" view contains all available configuration parameters for the service and its role types. This mode
   * performs validation on the configuration, which could take a few seconds on a large cluster (around 500 nodes or more).
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_config.html
   *
   * @return List of service and role types configuration parameters.
   */
  public ServiceConfig getConfig();

  /**
   * Retrieves the full configuration of a specific service.
   * @param fullView
   * @return
   */
  public ServiceConfig getConfig(boolean fullView);

  /**
   * Updates the service configuration with the given values.
   * <p/>
   * If a value is set in the given configuration, it will be added to the service's configuration, replacing any
   * existing entries. If a value is unset (its value is null), the existing configuration for the attribute will be
   * erased, if any.
   * <p/>
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_config.html
   *
   * @param newConfig Configuration changes.
   * @return The new service configuration.
   */
  public ServiceConfig updateConfig(ServiceConfig newConfig);

  /**
   * Fetch metric readings for a particular service.
   * <p/>
   * By default, this call will look up all metrics available for the service. If only specific metrics are desired,
   * use the metrics parameter.
   * <p/>
   * By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults to
   * the current server time). The from and to parameters can be used to control the window being queried.
   * A maximum window of 3 hours is enforced.
   * <p/>
   * When requesting a "full" view, aside from the extended properties of the returned metric data, the collection will
   * also contain information about all metrics available for the service, even if no readings are available in the
   * requested window.
   * <p/>
   * HDFS services from CDH4 that have more than one nameservice will not expose any metrics. Instead, the nameservices
   * should be queried separately.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_metrics.html
   *
   * @param from    Start of the period to query.
   * @param to      End of the period to query.
   * @param metrics Filter for which metrics to query.
   * @return List of readings from the monitors.
   */
  public MetricList getMetrics(long from, long to, String metrics);

  /**
   * List the nameservices of an HDFS service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_nameservices.html
   *
   * @return List of nameservices.
   */
  public NameServiceList getNameServices();

  /**
   * Retrieve information about a nameservice.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_nameservices_-nameservice-.html
   *
   * @param nameService The nameservice to retrieve.
   * @return Details of the nameservice.
   */
  public NameService getNameServiceDetails(String nameService);

  /**
   * Fetch metric readings for a particular nameservice.
   * <p/>
   * By default, this call will look up all metrics available. If only specific metrics are desired, use the metrics
   * parameter.
   * <p/>
   * By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults to
   * the current server time). The from and to parameters can be used to control the window being queried. A maximum
   * window of 3 hours is enforced.
   * <p/>
   * When requesting a "full" view, aside from the extended properties of the returned metric data, the collection will also contain information about all metrics available, even if no readings are available in the requested window.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_nameservices_-nameservice-_metrics.html
   *
   * @param nameservice The nameservice.
   * @param metrics     Filter for which metrics to query.
   * @param from        Start of the period to query.
   * @param to          End of the period to query.
   * @return List of readings from the monitors.
   */
  public MetricList getNameServiceMetrics(String nameservice, String metrics, long from, long to);

  /**
   * Bootstrap HDFS stand-by NameNodes.
   * <p/>
   * Submit a request to synchronize HDFS NameNodes with their assigned HA partners. The command requires that the
   * target NameNodes are part of existing HA pairs, which can be accomplished by setting the nameservice configuration
   * parameter in the NameNode's configuration.
   * <p/>
   * The HA partner must already be formatted and running for this command to run.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_hdfsBootstrapStandBy.html
   *
   * @param standByNodes The names of the stand-by NameNodes to bootstrap.
   * @return A list of submitted commands.
   */
  public BulkCommandList bootstrapHdfsStandby(RoleNameList standByNodes);

  /**
   * Format HDFS NameNodes.
   * <p/>
   * Submit a format request to a list of NameNodes on a service. Note that trying to format a previously formatted
   * NameNode will fail.
   * <p/>
   * Note about high availability: when two NameNodes are working in an HA pair, only one of them should be formatted.
   * <p/>
   * Bulk command operations are not atomic, and may contain partial failures. The returned list will contain references
   * to all successful commands, and a list of error messages identifying the roles on which the command failed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_hdfsFormat.html
   *
   * @param nameNodesToFormat The names of the NameNodes to format.
   * @return A list of submitted commands.
   */
  public BulkCommandList hdfsFormat(RoleNameList nameNodesToFormat);

  /**
   * Initialize HDFS HA failover controller metadata.
   * <p/>
   * The controllers being initialized must already exist and be properly configured. The command will make sure the
   * needed data is initialized for the controller to work.
   * <p/>
   * Only one controller per nameservice needs to be initialized.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_hdfsInitializeAutoFailover.html
   *
   * @param controllersToInitialize The names of the controllers to initialize.
   * @return A list of submitted commands.
   */
  public BulkCommandList hdfsInitializeAutoFailover(RoleNameList controllersToInitialize);

  /**
   * Initialize HDFS NameNodes' shared edit directory.
   * <p/>
   * Shared edit directories are used when two HDFS NameNodes are operating as a high-availability pair. This command
   * initializes the shared directory to include the necessary metadata.
   * <p/>
   * The provided role names should reflect one of the NameNodes in the respective HA pair; the role must be stopped
   * and its data directory must already have been formatted. The shared edits directory must be empty for this command
   * to succeed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_hdfsInitializeSharedDir.html
   *
   * @param roleNames The names of the NameNodes
   * @return A list of submitted commands.
   */
  public BulkCommandList hdfsInitializeSharedDir(RoleNameList roleNames);

  /**
   * Create / update the Hue database schema.
   * <p/>
   * This command is to be run whenever a new database has been specified or, as necessary, after an upgrade.
   * <p/>
   * This request should be sent to Hue servers only.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_hueSyncDb.html
   *
   * @param hueServerRoles The names of the Hue server roles.
   * @return A list of submitted commands.
   */
  public BulkCommandList hueSyncDb(RoleNameList hueServerRoles);

  /**
   * Refresh a role's data.
   * <p/>
   * For MapReduce services, this command should be executed on JobTracker roles. It refreshes the role's queue and
   * node information.
   * <p/>
   * For HDFS services, this command should be executed on NameNode roles. It refreshes the NameNode's node list.
   * <p/>
   * For Yarn services, this command should be executed on ResourceManager roles. It refreshes the role's queue and
   * node information.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_refresh.html
   *
   * @param rolesNames The names of the roles.
   * @return A list of submitted commands.
   */
  public BulkCommandList refresh(RoleNameList rolesNames);

  /**
   * Restart a set of role instances.
   * <p/>
   * Bulk command operations are not atomic, and may contain partial failures. The returned list will contain references
   * to all successful commands, and a list of error messages identifying the roles on which the command failed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_restart.html
   *
   * @param roleNames The name of the roles to restart.
   * @return A reference to the submitted command.
   */
  public BulkCommandList restart(RoleNameList roleNames);

  /**
   * Start a set of role instances.
   * <p/>
   * Bulk command operations are not atomic, and may contain partial failures. The returned list will contain
   * references to all successful commands, and a list of error messages identifying the roles on which the command failed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_start.html
   *
   * @param roleNames The names of the roles to start.
   * @return A reference to the submitted command.
   */
  public BulkCommandList start(RoleNameList roleNames);

  /**
   * Stop a set of role instances.
   * <p/>
   * Bulk command operations are not atomic, and may contain partial failures. The returned list will contain references
   * to all successful commands, and a list of error messages identifying the roles on which the command failed.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_stop.html
   *
   * @param roleNames The role type.
   * @return A reference to the submitted command.
   */
  public BulkCommandList stop(RoleNameList roleNames);

  /**
   * Cleanup a list of ZooKeeper server roles.
   * <p/>
   * This command removes snapshots and transaction log files kept by ZooKeeper for backup purposes. Refer to the
   * ZooKeeper documentation for more details.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_zooKeeperCleanup.html
   *
   * @param roleNames The names of the roles.
   * @return A list of submitted commands.
   */
  public BulkCommandList zooKeeperCleanUp(RoleNameList roleNames);

  /**
   * Initialize a list of ZooKeeper server roles.
   * <p/>
   * This applies to ZooKeeper services from CDH4. Before ZooKeeper server roles can be used, they need to be initialized.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roleCommands_zooKeeperInit.html
   *
   * @param roleNames The names of the roles.
   * @return A list of submitted commands.
   */
  public BulkCommandList zooKeeperInit(RoleNameList roleNames);

  /**
   * Lists all roles of a given service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles.html
   *
   * @return List of roles.
   */
  public RoleList getRoles();

  /**
   * Create new roles in a given service.
   * <ul>
   * <li><strong>HDFS (CDH3):</strong>	NAMENODE, DATANODE, SECONDARYNAMENODE, BALANCER, GATEWAY</li>
   * <li><strong>HDFS (CDH4):</strong>NAMENODE, DATANODE, SECONDARYNAMENODE, BALANCER, HTTPFS, FAILOVERCONTROLLER,
   * GATEWAY  </li>
   * <li><strong>MAPREDUCE:</strong>	JOBTRACKER, TASKTRACKER, GATEWAY</li>
   * <li><strong>HBASE:</strong>	MASTER, REGIONSERVER, GATEWAY</li>
   * <li><strong>YARN:</strong>RESROUCEMANAGER, NODEMANAGER, JOBHISTORY, GATEWAY</li>
   * <li><strong>OOZIE:</strong>	OOZIE_SERVER</li>
   * <li><strong>ZOOKEEPER:</strong>	SERVER</li>
   * <li><strong>HUE (CDH3):</strong>	HUE_SERVER, BEESWAX_SERVER, KT_RENEWER, JOBSUBD</li>
   * <li><strong>HUE (CDH4):</strong>	HUE_SERVER, BEESWAX_SERVER, KT_RENEWER</li>
   * </ul>
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles.html
   *
   * @param newRoles Roles to create.
   * @return List of created roles.
   */
  public RoleList createRoles(RoleList newRoles);

  /**
   * Retrieves detailed information about a role.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-.html
   *
   * @param roleName The role name.
   * @return The details of the role.
   */
  public Role getRoleDetails(String roleName);

  /**
   * Deletes a role from a given service.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-.html
   *
   * @param roleName The role name.
   * @return The details of the deleted role.
   */
  public Role deleteRole(String roleName);

  /**
   * List active role commands.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_commands.html
   *
   * @param roleName The role to query..
   * @return A list of active role commands.
   */
  public CommandList getActiveRoleCommands(String roleName);

  /**
   * Retrieves the configuration of a specific role. Note that the "full" view performs validation on the configuration,
   * which could take a few seconds on a large cluster (around 500 nodes or more).
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_config.html
   *
   * @param roleName The role to look up.
   * @return List of role configuration parameters.
   */
  public ConfigList getRoleConfig(String roleName);

  /**
   * Updates the role configuration with the given values.
   * <p/>
   * If a value is set in the given configuration, it will be added to the role's configuration, replacing any existing
   * entries. If a value is unset (its value is null), the existing configuration for the attribute will be erased, if any.
   * <p/>
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_config.html
   *
   * @param roleName  The role to modify.
   * @param newConfig Configuration changes.
   * @param message   Optional message describing the changes.
   * @return The new service configuration.
   */
  public ConfigList updateRoleConfig(String roleName, ConfigList newConfig, String message);

  /**
   * Same as {@link ServiceAPI#updateRoleConfig(String, com.axemblr.service.cm.models.cm.ConfigList)} without message.
   *
   * @param roleName
   * @param newConfig
   * @return
   */
  public ConfigList updateRoleConfig(String roleName, ConfigList newConfig);

  /**
   * Retrieves the log file for the role's main process.
   * <p/>
   * If the role is not started, this will be the log file associated with the last time the role was run.
   * <p/>
   * Log files are returned as plain text (type "text/plain").
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_logs_full.html
   *
   * @param roleName The role to start.
   * @return Contents of the role's log file.
   */
  public String getRoleFullLog(String roleName);

  /**
   * Retrieves the role's standard error output.
   * <p/>
   * If the role is not started, this will be the output associated with the last time the role was run.
   * <p/>
   * Log files are returned as plain text (type "text/plain").
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_logs_stderr.html
   *
   * @param roleName The role to start.
   * @return Contents of the role's standard error output.
   */
  public String getRoleStandrdError(String roleName);

  /**
   * Retrieves the role's standard output.
   * <p/>
   * If the role is not started, this will be the output associated with the last time the role was run.
   * <p/>
   * Log files are returned as plain text (type "text/plain").
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_logs_stdout.html
   *
   * @param roleName The role to start.
   * @return Contents of the role's standard output.
   */
  public String getRoleStandardOut(String roleName);

  /**
   * Fetch metric readings for a particular role.
   * <p/>
   * By default, this call will look up all metrics available for the role. If only specific metrics are desired, use
   * the metrics parameter.
   * <p/>
   * By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults
   * to the current server time). The from and to parameters can be used to control the window being queried.
   * A maximum window of 3 hours is enforced.
   * <p/>
   * When requesting a "full" view, aside from the extended properties of the returned metric data, the collection
   * will also contain information about all metrics available for the role, even if no readings are available in the
   * requested window.
   * <p/>
   * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services_-serviceName-_roles_-roleName-_metrics.html
   *
   * @param roleName The name of the role.
   * @param metrics  Filter for which metrics to query.
   * @param from     Start of the period to query.
   * @param to       End of the period to query.
   * @return List of readings from the monitors.
   */
  public MetricList getRoleMetrics(String roleName, String metrics, long from, long to);

}
