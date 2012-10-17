/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import com.axemblr.service.cm.models.cm.RoleRef;
import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * Provides information about an HDFS nameservice.
 * Nameservices can be either a stand-alone NameNode, a NameNode paired with a SecondaryNameNode,
 * or a high-availability pair formed by an active and a stand-by NameNode.
 * The following fields are only available in the object's full view:
 * <ul>
 * <li>healthSummary</li>
 * <li>healthChecks</li>
 * </ul>
 * <p/>
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiNameservice.html
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_nameservice.html
 */
public class NameService {
  private final String name;
  private final RoleRef active;
  private final RoleRef activeFailoverController;
  private final RoleRef standBy;
  private final RoleRef standByFailoverController;
  private final RoleRef secondary;
  private final ImmutableSet<String> mountPoints;
  private final HealthSummary healthSummary;
  private final ImmutableSet<HealthCheck> healthChecks;

  @JsonCreator
  public NameService(@JsonProperty("name") String name,
                     @JsonProperty("active") RoleRef active,
                     @JsonProperty("activeFailoverController") RoleRef activeFailoverController,
                     @JsonProperty("standBy") RoleRef standBy,
                     @JsonProperty("standByFailoverController") RoleRef standByFailoverController,
                     @JsonProperty("secondary") RoleRef secondary,
                     @JsonProperty("mountPoints") Set<String> mountPoints,
                     @JsonProperty("healthSummary") HealthSummary healthSummary,
                     @JsonProperty("healthChecks") Set<HealthCheck> healthChecks) {
    this.name = name;
    this.active = active;
    this.activeFailoverController = activeFailoverController;
    this.standBy = standBy;
    this.standByFailoverController = standByFailoverController;
    this.secondary = secondary;
    this.mountPoints = (mountPoints == null) ? ImmutableSet.<String>of() : ImmutableSet.copyOf(mountPoints);
    this.healthSummary = healthSummary;
    this.healthChecks = (healthChecks == null) ? ImmutableSet.<HealthCheck>of() : ImmutableSet.copyOf(healthChecks);
  }

  /**
   * Name of the nameservice.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Reference to the active NameNode.
   *
   * @return
   */
  public RoleRef getActive() {
    return active;
  }

  /**
   * Reference to the active NameNode's failover controller, if configured.
   *
   * @return
   */
  public RoleRef getActiveFailoverController() {
    return activeFailoverController;
  }

  /**
   * Reference to the stand-by NameNode.
   *
   * @return
   */
  public RoleRef getStandBy() {
    return standBy;
  }

  /**
   * Reference to the stand-by NameNode's failover controller, if configured.
   *
   * @return
   */
  public RoleRef getStandByFailoverController() {
    return standByFailoverController;
  }

  /**
   * Reference to the SecondaryNameNode.
   *
   * @return
   */
  public RoleRef getSecondary() {
    return secondary;
  }

  /**
   * Mount points assigned to this nameservice in a federation.
   *
   * @return
   */
  public ImmutableSet<String> getMountPoints() {
    return mountPoints;
  }

  /**
   * Requires "full" view. The high-level health status of this nameservice.
   *
   * @return
   */
  public HealthSummary getHealthSummary() {
    return healthSummary;
  }

  /**
   * Requires "full" view. List of health checks performed on the nameservice.
   *
   * @return
   */
  public ImmutableSet<HealthCheck> getHealthChecks() {
    return healthChecks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NameService)) return false;

    NameService that = (NameService) o;

    if (active != null ? !active.equals(that.active) : that.active != null) return false;
    if (activeFailoverController != null ? !activeFailoverController.equals(that.activeFailoverController) : that.activeFailoverController != null)
      return false;
    if (healthChecks != null ? !healthChecks.equals(that.healthChecks) : that.healthChecks != null) return false;
    if (healthSummary != that.healthSummary) return false;
    if (mountPoints != null ? !mountPoints.equals(that.mountPoints) : that.mountPoints != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (secondary != null ? !secondary.equals(that.secondary) : that.secondary != null) return false;
    if (standBy != null ? !standBy.equals(that.standBy) : that.standBy != null) return false;
    if (standByFailoverController != null ? !standByFailoverController.equals(that.standByFailoverController) : that.standByFailoverController != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (active != null ? active.hashCode() : 0);
    result = 31 * result + (activeFailoverController != null ? activeFailoverController.hashCode() : 0);
    result = 31 * result + (standBy != null ? standBy.hashCode() : 0);
    result = 31 * result + (standByFailoverController != null ? standByFailoverController.hashCode() : 0);
    result = 31 * result + (secondary != null ? secondary.hashCode() : 0);
    result = 31 * result + (mountPoints != null ? mountPoints.hashCode() : 0);
    result = 31 * result + (healthSummary != null ? healthSummary.hashCode() : 0);
    result = 31 * result + (healthChecks != null ? healthChecks.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "NameService{" +
        "name='" + name + '\'' +
        ", active=" + active +
        ", activeFailoverController=" + activeFailoverController +
        ", standBy=" + standBy +
        ", standByFailoverController=" + standByFailoverController +
        ", secondary=" + secondary +
        ", mountPoints=" + mountPoints +
        ", healthSummary=" + healthSummary +
        ", healthChecks=" + healthChecks +
        '}';
  }
}
