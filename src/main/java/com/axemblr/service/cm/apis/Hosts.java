/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.hosts.Host;
import com.axemblr.service.cm.models.hosts.HostList;
import com.axemblr.service.cm.models.hosts.MetricList;

import java.util.List;
import java.util.Set;

/**
 * Hosts API operations for Cloudera Manager.
 * <p/>
 * The Cloudera Manager Agent runs on hosts that are managed by Cloudera Manager. You can assign service roles to hosts.
 * <p/>
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts.html
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-.html
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-_config.html
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__hosts_-hostId-_metrics.html
 */
public interface Hosts {

  public static final String BASE_PATH = "/hosts";

  /**
   * Create one or more hosts. You must specify at least the hostname and ipAddress in the request objects.
   * If no hostId is specified, it will be set to the hostname. It is an error to try and create host with the same
   * hostId as another host.
   *
   * @param hostsToCreate The list of hosts to create.
   * @return The newly created host objects.
   */
  public HostList createHosts(Set<Host> hostsToCreate);

  /**
   * Returns the hostIds for all hosts in the system.
   *
   * @return A list of hostIds.
   */
  public HostList list();

  /**
   * Delete all hosts in the system.
   *
   * @return The list of deleted hosts.
   */
  public HostList deleteAll();

  /**
   * Returns a specific Host in the system.
   *
   * @param hostId The ID of the host to read
   * @return The Host object with the specified hostId
   */
  public Host getHostById(String hostId);


  /**
   * Update an existing host in the system. Currently, only updating the rackId is supported.
   * All other fields of the host will be ignored.
   *
   * @param hostId      The hostId to update.
   * @param updatedHost The updated host object.
   * @return The updated host.
   */
  public Host updateHost(String hostId, Host updatedHost);


  /**
   * Delete a host from the system.
   *
   * @param hostId The ID of the host to remove.
   * @return The deleted Host.
   */
  public Host removeHostById(String hostId);

  /**
   * Delete a host from the system.
   *
   * @param host the host to remove.
   * @return The deleted Host.
   */
  public Host removeHost(Host host);

  /**
   * Retrieves the configuration of a specific host.
   *
   * @param hostId The ID of the host.
   * @return List of host configuration parameters.
   */
  public ConfigList getHostConfig(String hostId);

  /**
   * Retrieves the configuration of a specific host.
   *
   * @param host The host to get the config for.
   * @return List of host configuration parameters.
   */
  public ConfigList getHostConfig(Host host);

  /**
   * Updates the host configuration with the given values.
   * <p/>
   * If a value is set in the given configuration, it will be added to the host's configuration,
   * replacing any existing entries.
   * <p/>
   * If a value is unset (its value is null), the existing configuration for the attribute will be erased, if any.
   * <p/>
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param hostId    The ID of the host.
   * @param newConfig Configuration changes.
   * @return The new host configuration.
   */
  public ConfigList updateHostConfig(String hostId, List<Config> newConfig);

  /**
   * Updates the host configuration with the given values.
   * <p/>
   * If a value is set in the given configuration, it will be added to the host's configuration,
   * replacing any existing entries.
   * <p/>
   * If a value is unset (its value is null), the existing configuration for the attribute will be erased, if any.
   * <p/>
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param host      The host.
   * @param newConfig Configuration changes.
   * @return The new host configuration.
   */
  public ConfigList updateHostConfig(Host host, List<Config> newConfig);

  /**
   * Updates the host configuration with the given values.
   * <p/>
   * If a value is set in the given configuration, it will be added to the host's configuration,
   * replacing any existing entries.
   * <p/>
   * If a value is unset (its value is null), the existing configuration for the attribute will be erased, if any.
   * <p/>
   * Attributes that are not listed in the input will maintain their current values in the configuration.
   *
   * @param hostId    The ID of the host.
   * @param newConfig Configuration changes.
   * @param message   Optional message describing the changes.
   * @return The new host configuration.
   */
  public ConfigList updateHostConfig(String hostId, List<Config> newConfig, String message);

  /**
   * <h2>Fetch metric readings for a host.</h2>
   * <p/>
   * By default, this call will look up all metrics available for the host.
   * If only specific metrics are desired, use the metrics parameter.
   * <p/>
   * By default, the returned results correspond to a 5 minute window based on the provided end time (which defaults
   * to the current server time). The from and to parameters can be used to control the window being queried.
   * A maximum window of 3 hours is enforced.
   * <p/>
   * When requesting a "full" view, aside from the extended properties of the returned metric data, the collection
   * will also contain information about all metrics available for the role, even if no readings are available in the
   * requested window.
   * <p/>
   * Host metrics also include per-network interface and per-storage device metrics. Since collecting this data incurs
   * in more overhead, query parameters can be used to choose which network interfaces and storage devices to query,
   * or to these metrics altogether.
   * <p/>
   * Storage metrics are collected at different levels; for example, per-disk and per-partition metrics are available.
   * The "storageIds" parameter can be used to filter specific storage IDs. In the returned data, the network
   * interfaces and storage IDs can be identified by looking at the "context" property of the metric objects.
   *
   * @param hostId       The host's ID.
   * @param startDate    Start of the period to query.
   * @param endDate      End of the period to query. Default 'now'.
   * @param queryNetwork Whether to query for network interface metrics. Default 'true'.
   * @param interfaces   Network interfaces to query for metrics (default = all).
   * @param queryStorage Whether to query for storage metrics. Default 'true'.
   * @param storageIds   Storage context IDs to query for metrics (default = all).
   * @param metrics      Filter for which metrics to query.
   * @return List of readings from the monitors.
   */
  public MetricList getHostMetrics(String hostId,
                                   String startDate,
                                   String endDate,
                                   boolean queryNetwork,
                                   String interfaces,
                                   boolean queryStorage,
                                   String storageIds,
                                   String metrics);
}
