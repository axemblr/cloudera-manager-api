/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.axemblr.service.cm.models.clusters.HaStatus;
import com.axemblr.service.cm.models.clusters.HealthCheck;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.clusters.ServiceRef;
import com.axemblr.service.cm.models.hosts.HostRef;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A role represents a specific entity that participate in a service. Examples are JobTrackers, DataNodes,
 * HBase Masters. Each role is assigned a host where it runs on.
 * <p/>
 * A role performs specific actions for a service, and is assigned to a host. It usually runs as a daemon process,
 * such as a DataNode or a TaskTracker. (There are exceptions; not all roles are daemon processes.) Once created, a role
 * cannot be reassigned to a different host. You need to delete and re-create it.
 * <p/>
 * A role has its own configuration, status and metrics. API commands on roles are always issued in bulk at the service
 * level.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_role.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRole.html
 */
public class Role {
  private final String name;
  private final RoleType type;
  private final HostRef hostRef;
  private final ServiceRef serviceRef;
  private final RoleState roleState;
  private final HealthSummary healthSummary;
  private final ImmutableSet<HealthCheck> healthChecks;
  private final boolean configStale;
  private final HaStatus haStatus;
  private final String roleUrl;


  public Role(String name, RoleType type, HostRef hostRef) {
    this(name, type, hostRef, null, null, null, null, false, null, null);
  }

  @JsonCreator
  public Role(@JsonProperty("name") String name,
              @JsonProperty("type") RoleType type,
              @JsonProperty("hostRef") HostRef hostRef,
              @JsonProperty("serviceRef") ServiceRef serviceRef,
              @JsonProperty("roleState") RoleState roleState,
              @JsonProperty("healthSummary") HealthSummary healthSummary,
              @JsonProperty("healthChecks") Set<HealthCheck> healthChecks,
              @JsonProperty("configStale") boolean configStale,
              @JsonProperty("haStatus") HaStatus haStatus,
              @JsonProperty("roleUrl") String roleUrl) {
    this.name = name;
    this.type = type;
    this.hostRef = hostRef;
    this.serviceRef = serviceRef;
    this.roleState = roleState;
    this.healthSummary = healthSummary;
    this.healthChecks = (healthChecks == null) ? ImmutableSet.<HealthCheck>of() : ImmutableSet.copyOf(healthChecks);
    this.configStale = configStale;
    this.haStatus = haStatus;
    this.roleUrl = roleUrl;
  }

  /**
   * The name of the role.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The type of the role, e.g. NAMENODE, DATANODE, TASKTRACKER.
   *
   * @return
   */
  public RoleType getType() {
    return type;
  }

  /**
   * A reference to the host where this role runs.
   *
   * @return
   */
  public HostRef getHostRef() {
    return hostRef;
  }

  /**
   * Readonly. A reference to the parent service.
   *
   * @return
   */
  public ServiceRef getServiceRef() {
    return serviceRef;
  }

  /**
   * Readonly. The configured run state of this role. Whether it's running, etc.
   *
   * @return
   */
  @JsonIgnore
  public RoleState getRoleState() {
    return roleState;
  }

  /**
   * Readonly. The high-level health status of this role.
   *
   * @return
   */
  @JsonIgnore
  public HealthSummary getHealthSummary() {
    return healthSummary;
  }

  /**
   * Readonly. The list of health checks of this service.
   *
   * @return
   */
  @JsonIgnore
  public ImmutableSet<HealthCheck> getHealthChecks() {
    return healthChecks;
  }

  /**
   * Configuration stale: undocumented in the API.
   *
   * @return
   */
  @JsonIgnore
  public boolean isConfigStale() {
    return configStale;
  }

  /**
   * Readonly. The HA status of this role.
   *
   * @return
   */
  @JsonIgnore
  public HaStatus getHaStatus() {
    return haStatus;
  }

  /**
   * Readonly. Link into the Cloudera Manager web UI for this specific role.
   *
   * @return
   */
  @JsonIgnore
  public String getRoleUrl() {
    return roleUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Role)) return false;

    Role role = (Role) o;

    if (hostRef != null ? !hostRef.equals(role.hostRef) : role.hostRef != null) return false;
    if (name != null ? !name.equals(role.name) : role.name != null) return false;
    if (type != role.type) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (hostRef != null ? hostRef.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Role{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", hostRef=" + hostRef +
        ", serviceRef=" + serviceRef +
        ", roleState=" + roleState +
        ", healthSummary=" + healthSummary +
        ", healthChecks=" + healthChecks +
        ", haStatus=" + haStatus +
        ", roleUrl='" + roleUrl + '\'' +
        '}';
  }
}
