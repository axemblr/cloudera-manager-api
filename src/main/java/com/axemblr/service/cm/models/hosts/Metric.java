/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.hosts;

import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A metric represents a specific metric monitored by the Cloudera Management Services,
 * and a list of values matching a user query.
 * These fields are available only in the "full" view:
 * <ul>
 * <li>displayName</li>
 * <li>description</li>
 * </ul>
 * <br/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiMetric.html
 */
public class Metric {
  private final String name;
  private final String context;
  private final String unit;
  private final ImmutableList<MetricData> data;
  private final String displayName;
  private final String description;

  @JsonCreator
  public Metric(@JsonProperty("name") String name,
                @JsonProperty("context") String context,
                @JsonProperty("unit") String unit,
                @JsonProperty("data") List<MetricData> data,
                @JsonProperty("displayName") String displayName,
                @JsonProperty("description") String description) {
    this.name = name;
    this.context = context;
    this.unit = unit;
    this.data = (data == null) ? ImmutableList.<MetricData>of() : ImmutableList.copyOf(data);
    this.displayName = displayName;
    this.description = description;
  }

  /**
   * Name of the metric.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Context the metric is associated with.
   *
   * @return
   */
  public String getContext() {
    return context;
  }

  /**
   * Unit of the metric values.
   *
   * @return
   */
  public String getUnit() {
    return unit;
  }

  /**
   * List of readings retrieved from the monitors.
   *
   * @return
   */
  public ImmutableList<MetricData> getData() {
    return data;
  }

  /**
   * Requires "full" view. User-friendly display name for the metric.
   *
   * @return
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Requires "full" view. Description of the metric.
   *
   * @return
   */
  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Metric)) return false;

    Metric metric = (Metric) o;

    if (context != null ? !context.equals(metric.context) : metric.context != null) return false;
    if (data != null ? !data.equals(metric.data) : metric.data != null) return false;
    if (description != null ? !description.equals(metric.description) : metric.description != null) return false;
    if (displayName != null ? !displayName.equals(metric.displayName) : metric.displayName != null) return false;
    if (name != null ? !name.equals(metric.name) : metric.name != null) return false;
    if (unit != null ? !unit.equals(metric.unit) : metric.unit != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (context != null ? context.hashCode() : 0);
    result = 31 * result + (unit != null ? unit.hashCode() : 0);
    result = 31 * result + (data != null ? data.hashCode() : 0);
    result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Metric{" +
        "name='" + name + '\'' +
        ", context='" + context + '\'' +
        ", unit='" + unit + '\'' +
        ", data=" + data +
        ", displayName='" + displayName + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
