/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A roleRef references a role. Each role is identified by its "roleName", the "serviceName" for the service it belongs
 * to, and the "clusterName" in which the service resides.
 * To operate on the role object, use the API with the those fields as parameters.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_roleRef.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiRoleRef.html
 */
public class RoleRef {
  private final String clusterName;
  private final String serviceName;
  private final String roleName;

  @JsonCreator
  public RoleRef(@JsonProperty("clusterName") String clusterName,
                 @JsonProperty("serviceName") String serviceName,
                 @JsonProperty("roleName") String roleName) {
    this.clusterName = clusterName;
    this.serviceName = serviceName;
    this.roleName = roleName;
  }

  /**
   * The cluster in which the service referenced by this role resides.
   *
   * @return
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * The service name this role belongs to.
   *
   * @return
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * The identity of the role.
   *
   * @return
   */
  public String getRoleName() {
    return roleName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoleRef)) return false;

    RoleRef roleRef = (RoleRef) o;

    if (clusterName != null ? !clusterName.equals(roleRef.clusterName) : roleRef.clusterName != null) return false;
    if (roleName != null ? !roleName.equals(roleRef.roleName) : roleRef.roleName != null) return false;
    if (serviceName != null ? !serviceName.equals(roleRef.serviceName) : roleRef.serviceName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = clusterName != null ? clusterName.hashCode() : 0;
    result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
    result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RoleRef{" +
        "clusterName='" + clusterName + '\'' +
        ", serviceName='" + serviceName + '\'' +
        ", roleName='" + roleName + '\'' +
        '}';
  }
}
