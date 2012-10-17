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
 * Health check.
 */
public class HealthCheck {
  private final String name;
  private final HealthSummary summary;

  @JsonCreator
  public HealthCheck(@JsonProperty("name") String name,
                     @JsonProperty("summary") HealthSummary summary) {
    this.name = name;
    this.summary = summary;
  }

  /**
   * Unique name of this health check.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The summary status of this check.
   *
   * @return
   */
  public HealthSummary getSummary() {
    return summary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HealthCheck)) return false;

    HealthCheck that = (HealthCheck) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (summary != that.summary) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (summary != null ? summary.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "HealthCheck{" +
        "name='" + name + '\'' +
        ", summary=" + summary +
        '}';
  }
}
