/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.hosts;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A single data point of metric data.
 * <br/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiMetricData.html
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_metricData.html
 */
public class MetricData {
  private final long timestamp;
  private final double value;

  @JsonCreator
  public MetricData(@JsonProperty("timestamp") long timestamp,
                    @JsonProperty("value") double value) {
    this.timestamp = timestamp;
    this.value = value;
  }

  /**
   * When the metric reading was collected.
   *
   * @return
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * The value of the metric.
   *
   * @return
   */
  public double getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MetricData)) return false;

    MetricData that = (MetricData) o;

    if (timestamp != that.timestamp) return false;
    if (Double.compare(that.value, value) != 0) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (int) (timestamp ^ (timestamp >>> 32));
    temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L;
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "MetricData{" +
        "timestamp=" + timestamp +
        ", value=" + value +
        '}';
  }
}
