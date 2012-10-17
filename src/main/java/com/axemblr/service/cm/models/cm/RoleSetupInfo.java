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
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * Role configuration information used when creating services.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_roleSetupInfo.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleSetupInfo.html
 */
public class RoleSetupInfo extends Role {

  private final ConfigList config;

  @JsonCreator
  public RoleSetupInfo(@JsonProperty("name") String name,
                       @JsonProperty("type") RoleType type,
                       @JsonProperty("hostRef") HostRef hostRef,
                       @JsonProperty("serviceRef") ServiceRef serviceRef,
                       @JsonProperty("roleRef") RoleState roleState,
                       @JsonProperty("healthSummary") HealthSummary healthSummary,
                       @JsonProperty("healthChecks") Set<HealthCheck> healthChecks,
                       @JsonProperty("configStale") boolean confgiStale,
                       @JsonProperty("haStatus") HaStatus haStatus,
                       @JsonProperty("roleUrl") String roleUrl,
                       @JsonProperty("config") ConfigList config) {
    super(name, type, hostRef, serviceRef, roleState, healthSummary, healthChecks, confgiStale, haStatus, roleUrl);
    this.config = config;
  }

  /**
   * The role configuration. Optional.
   *
   * @return
   */
  public ConfigList getConfig() {
    return config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleSetupInfo)) return false;
    if (!super.equals(o)) return false;

    RoleSetupInfo that = (RoleSetupInfo) o;

    if (config != null ? !config.equals(that.config) : that.config != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (config != null ? config.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RoleSetupInfo{" +
        "config=" + config +
        '}';
  }
}
