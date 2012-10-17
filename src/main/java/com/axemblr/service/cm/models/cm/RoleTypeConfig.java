/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Role type configuration information.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleTypeConfig.html
 */
public class RoleTypeConfig extends ConfigList {
  private final RoleType roleType;

  @JsonCreator
  public RoleTypeConfig(@JsonProperty("items") List<Config> items,
                        @JsonProperty("roleType") RoleType roleType) {
    super(items);
    this.roleType = checkNotNull(roleType);
  }

  /**
   * The role type.
   *
   * @return
   */
  public RoleType getRoleType() {
    return roleType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleTypeConfig)) return false;
    if (!super.equals(o)) return false;

    RoleTypeConfig that = (RoleTypeConfig) o;

    if (roleType != that.roleType) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RoleTypeConfig{" +
        "roleType='" + roleType + '\'' +
        '}';
  }
}
