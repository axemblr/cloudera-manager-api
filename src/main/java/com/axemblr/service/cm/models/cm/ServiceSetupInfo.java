/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.axemblr.service.cm.models.clusters.ClusterRef;
import com.axemblr.service.cm.models.clusters.HealthCheck;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.clusters.ServiceConfig;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * Service configuration information.
 * This object is used to configure a new service, complete with a set of roles and the respective configurations.
 * See the parent class ApiService for minimal specification, which is simply the service name and type.
 * Note that all fields here are optional. The semantics of not providing a value may change depending on the call
 * being made. Refer to the documentation of the appropriate call for the behavior of optional fields.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_setupInfo.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiServiceSetupInfo.html
 */
public class ServiceSetupInfo extends Service {
  private final ServiceConfig config;
  private final ImmutableSet<RoleSetupInfo> roles;

  public ServiceSetupInfo(String name, ServiceType type) {
    this(name, type, null, null, null, false, null, null, null, null);
  }

  @JsonCreator
  public ServiceSetupInfo(@JsonProperty("name") String name,
                          @JsonProperty("type") ServiceType type,
                          @JsonProperty("clusterRef") ClusterRef clusterRef,
                          @JsonProperty("serviceState") ServiceState serviceState,
                          @JsonProperty("healthSummary") HealthSummary healthSummary,
                          @JsonProperty("configStale") boolean configStale,
                          @JsonProperty("healthChecks") Set<HealthCheck> healthChecks,
                          @JsonProperty("serviceUrl") String serviceUrl,
                          @JsonProperty("config") ServiceConfig config,
                          @JsonProperty("roles") Set<RoleSetupInfo> roles) {
    super(name, type, clusterRef, serviceState, healthSummary, configStale, healthChecks, serviceUrl);
    this.config = config;
    this.roles = (roles == null) ? ImmutableSet.<RoleSetupInfo>of() : ImmutableSet.copyOf(roles);
  }

  /**
   * Configuration of the service being created. Optional.
   *
   * @return
   */
  public ServiceConfig getConfig() {
    return config;
  }

  /**
   * The list of service roles. Optional.
   *
   * @return
   */
  public ImmutableSet<RoleSetupInfo> getRoles() {
    return roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceSetupInfo)) return false;
    if (!super.equals(o)) return false;

    ServiceSetupInfo that = (ServiceSetupInfo) o;

    if (config != null ? !config.equals(that.config) : that.config != null) return false;
    if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (config != null ? config.hashCode() : 0);
    result = 31 * result + (roles != null ? roles.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ServiceSetupInfo{" +
        "config=" + config +
        ", roles=" + roles +
        '}';
  }
}
