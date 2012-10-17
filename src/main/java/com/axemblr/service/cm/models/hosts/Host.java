/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.hosts;

import com.axemblr.service.cm.models.clusters.HealthCheck;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.cm.RoleRef;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * This is the model for a host in the system.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_host.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiHost.html
 */
public class Host {
  private final String hostId;
  private final String ipAddress;
  private final String hostname;
  private final String rackId;
  private final DateTime lastHeartbeat;
  private final ImmutableSet<RoleRef> roleRefs;
  private final HealthSummary healthSummary;
  private final ImmutableSet<HealthCheck> healthChecks;
  private final String hostUrl;

  public Host(String ipAddress, String hostname) {
    this(null, ipAddress, hostname, null, null, null, null, null, null);
  }

  public Host(String hostId, String ipAddress, String hostname, String rackId) {
    this(hostId, ipAddress, hostname, rackId, null, null, null, null, null);
  }

  @JsonCreator
  public Host(@JsonProperty("hostId") String hostId,
              @JsonProperty("ipAddress") String ipAddress,
              @JsonProperty("hostname") String hostname,
              @JsonProperty("rackId") String rackId,
              @JsonProperty("lastHeartbeat") DateTime lastHeartbeat,
              @JsonProperty("roleRefs") Set<RoleRef> roleRefs,
              @JsonProperty("healthSummary") HealthSummary healthSummary,
              @JsonProperty("healthChecks") Set<HealthCheck> healthChecks,
              @JsonProperty("hostUrl") String hostUrl) {
    this.hostId = hostId;
    this.ipAddress = ipAddress;
    this.hostname = hostname;
    this.rackId = rackId;
    this.lastHeartbeat = lastHeartbeat;
    this.roleRefs = (roleRefs == null) ? ImmutableSet.<RoleRef>of() : ImmutableSet.copyOf(roleRefs);
    this.healthSummary = healthSummary;
    this.healthChecks = (healthChecks == null) ? ImmutableSet.<HealthCheck>of() : ImmutableSet.copyOf(healthChecks);
    this.hostUrl = hostUrl;
  }

  /**
   * A unique host identifier. Typically identical to the hostname, i.e. the host's FQDN.
   *
   * @return
   */
  public String getHostId() {
    return hostId;
  }

  /**
   * The host IP address. This field is not mutable after the initial creation.
   *
   * @return
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * The hostname. This field is not mutable after the initial creation.
   *
   * @return
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * The rack ID for this host.
   *
   * @return
   */
  public String getRackId() {
    return rackId;
  }

  /**
   * Readonly. Requires "full" view. When the host agent sent the last heartbeat.
   *
   * @return
   */
  @JsonIgnore
  public DateTime getLastHeartbeat() {
    return lastHeartbeat;
  }

  /**
   * Readonly. Requires "full" view. The list of roles assigned to this host.
   *
   * @return
   */
  @JsonIgnore
  public ImmutableSet<RoleRef> getRoleRefs() {
    return roleRefs;
  }

  /**
   * Readonly. Requires "full" view. The high-level health status of this host.
   *
   * @return
   */
  @JsonIgnore
  public HealthSummary getHealthSummary() {
    return healthSummary;
  }

  /**
   * Readonly. Requires "full" view. The list of health checks performed on the host, with their results.
   *
   * @return
   */
  @JsonIgnore
  public ImmutableSet<HealthCheck> getHealthChecks() {
    return healthChecks;
  }

  /**
   * Readonly. A URL into the Cloudera Manager web UI for this specific host.
   *
   * @return
   */
  @JsonIgnore
  public String getHostUrl() {
    return hostUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Host)) return false;

    Host host = (Host) o;

    if (hostId != null ? !hostId.equals(host.hostId) : host.hostId != null) return false;
    if (hostname != null ? !hostname.equals(host.hostname) : host.hostname != null) return false;
    if (ipAddress != null ? !ipAddress.equals(host.ipAddress) : host.ipAddress != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = hostId != null ? hostId.hashCode() : 0;
    result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
    result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Host{" +
        "hostId='" + hostId + '\'' +
        ", ipAddress='" + ipAddress + '\'' +
        ", hostname='" + hostname + '\'' +
        ", rackId='" + rackId + '\'' +
        ", lastHeartbeat=" + lastHeartbeat +
        ", roleRefs=" + roleRefs +
        ", healthSummary=" + healthSummary +
        ", healthChecks=" + healthChecks +
        ", hostUrl='" + hostUrl + '\'' +
        '}';
  }
}
