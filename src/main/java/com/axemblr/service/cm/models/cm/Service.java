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
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * A service (such as HDFS, MapReduce, HBase) runs in a cluster. It has roles, which are the actual entities (NameNode,
 * DataNodes, etc.) that perform the service's functions.
 * <h2>HDFS services and health checks</h2>
 * <p/>
 * In CDH4, HDFS services may not present any health checks. This will happen if the service has more than
 * one nameservice configured. In those cases, the health information will be available by fetching information
 * about the nameservices instead. The health summary is still available, and reflects a service-wide summary.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiService.html
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_service.html
 */
public class Service {
  private final String name;
  private final ServiceType type;
  private final ClusterRef clusterRef;
  private final ServiceState serviceState;
  private final HealthSummary healthSummary;
  private final boolean configStale;
  private final ImmutableSet<HealthCheck> healthChecks;
  private final String serviceUrl;

  @JsonCreator
  public Service(@JsonProperty("name") String name,
                 @JsonProperty("type") ServiceType type,
                 @JsonProperty("clusterRef") ClusterRef clusterRef,
                 @JsonProperty("serviceState") ServiceState serviceState,
                 @JsonProperty("healthSummary") HealthSummary healthSummary,
                 @JsonProperty("configStale") boolean configStale,
                 @JsonProperty("healthChecks") Set<HealthCheck> healthChecks,
                 @JsonProperty("serviceUrl") String serviceUrl) {
    this.name = name;
    this.type = type;
    this.clusterRef = clusterRef;
    this.serviceState = serviceState;
    this.healthSummary = healthSummary;
    this.configStale = configStale;
    this.healthChecks = (healthChecks == null) ? ImmutableSet.<HealthCheck>of() : ImmutableSet.copyOf(healthChecks);
    this.serviceUrl = serviceUrl;
  }

  /**
   * The name of the service.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The type of the service, e.g. HDFS, MAPREDUCE, HBASE.
   *
   * @return
   */
  public ServiceType getType() {
    return type;
  }

  /**
   * Readonly. A reference to the enclosing cluster.
   *
   * @return
   */
  @JsonIgnore
  public ClusterRef getClusterRef() {
    return clusterRef;
  }

  /**
   * Readonly. The configured run state of this service. Whether it's running, etc.
   *
   * @return
   */
  @JsonIgnore
  public ServiceState getServiceState() {
    return serviceState;
  }

  /**
   * Readonly. The high-level health status of this service.
   *
   * @return
   */
  @JsonIgnore
  public HealthSummary getHealthSummary() {
    return healthSummary;
  }

  /**
   * Readonly. Expresses whether the service configuration is stale.
   *
   * @return
   */
  @JsonIgnore
  public boolean isConfigStale() {
    return configStale;
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
   * Readonly. Link into the Cloudera Manager web UI for this specific service.
   *
   * @return
   */
  @JsonIgnore
  public String getServiceUrl() {
    return serviceUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Service)) return false;

    Service service = (Service) o;

    if (configStale != service.configStale) return false;
    if (clusterRef != null ? !clusterRef.equals(service.clusterRef) : service.clusterRef != null) return false;
    if (healthChecks != null ? !healthChecks.equals(service.healthChecks) : service.healthChecks != null) return false;
    if (healthSummary != service.healthSummary) return false;
    if (name != null ? !name.equals(service.name) : service.name != null) return false;
    if (serviceState != service.serviceState) return false;
    if (serviceUrl != null ? !serviceUrl.equals(service.serviceUrl) : service.serviceUrl != null) return false;
    if (type != null ? !type.equals(service.type) : service.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (clusterRef != null ? clusterRef.hashCode() : 0);
    result = 31 * result + (serviceState != null ? serviceState.hashCode() : 0);
    result = 31 * result + (healthSummary != null ? healthSummary.hashCode() : 0);
    result = 31 * result + (configStale ? 1 : 0);
    result = 31 * result + (healthChecks != null ? healthChecks.hashCode() : 0);
    result = 31 * result + (serviceUrl != null ? serviceUrl.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Service{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", clusterRef=" + clusterRef +
        ", serviceState=" + serviceState +
        ", healthSummary=" + healthSummary +
        ", configStale=" + configStale +
        ", healthChecks=" + healthChecks +
        ", serviceUrl='" + serviceUrl + '\'' +
        '}';
  }
}
