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
 * A list of Metric objects.
 * <br/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_metricList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiMetricList.html
 */
public class MetricList {
  private final ImmutableList<Metric> items;

  @JsonCreator
  public MetricList(@JsonProperty("items") List<Metric> items) {
    this.items = (items == null) ? ImmutableList.<Metric>of() : ImmutableList.copyOf(items);
  }

  public ImmutableList<Metric> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MetricList)) return false;

    MetricList that = (MetricList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "MetricList{" +
        "items=" + items +
        '}';
  }
}
