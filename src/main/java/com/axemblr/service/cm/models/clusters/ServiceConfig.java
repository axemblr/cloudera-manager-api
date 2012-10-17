/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.RoleTypeConfig;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Set;

/**
 * Service and role type configuration.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_serviceConfig.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiServiceConfig.html
 */
public class ServiceConfig extends ConfigList {

  private final ImmutableSet<RoleTypeConfig> roleTypeConfigs;

  @JsonCreator
  public ServiceConfig(@JsonProperty("items") List<Config> items,
                       @JsonProperty("roleTypeConfigs") Set<RoleTypeConfig> roleTypeConfigs) {
    super(items);
    this.roleTypeConfigs = (roleTypeConfigs == null) ?
        ImmutableSet.<RoleTypeConfig>of() : ImmutableSet.copyOf(roleTypeConfigs);
  }

  /**
   * List of role type configurations.
   *
   * @return
   */
  public ImmutableSet<RoleTypeConfig> getRoleTypeConfigs() {
    return roleTypeConfigs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceConfig)) return false;
    if (!super.equals(o)) return false;

    ServiceConfig that = (ServiceConfig) o;

    if (roleTypeConfigs != null ? !roleTypeConfigs.equals(that.roleTypeConfigs) : that.roleTypeConfigs != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (roleTypeConfigs != null ? roleTypeConfigs.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ServiceConfig{" +
        "roleTypeConfigs=" + roleTypeConfigs +
        '}' + super.toString();
  }
}
