/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A serviceRef references a service. It is identified by the "serviceName" and "clusterName", the name of the cluster
 * which the service belongs to. To operate on the service object, use the API with those fields as parameters.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_serviceRef.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiServiceRef.html
 */
public class ServiceRef {
  private final String clusterName;
  private final String serviceName;

  @JsonCreator
  public ServiceRef(@JsonProperty("clusterName") String clusterName,
                    @JsonProperty("serviceName") String serviceName) {
    this.clusterName = clusterName;
    this.serviceName = serviceName;
  }

  /**
   * The enclosing cluster for this service.
   *
   * @return
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * The service name.
   *
   * @return
   */
  public String getServiceName() {
    return serviceName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceRef)) return false;

    ServiceRef that = (ServiceRef) o;

    if (clusterName != null ? !clusterName.equals(that.clusterName) : that.clusterName != null) return false;
    if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = clusterName != null ? clusterName.hashCode() : 0;
    result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ServiceRef{" +
        "clusterName='" + clusterName + '\'' +
        ", serviceName='" + serviceName + '\'' +
        '}';
  }
}
