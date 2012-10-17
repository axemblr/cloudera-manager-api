/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * List of activities.
 * <br/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_activityList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiActivityList.html
 */
public class ActivityList {

  private final ImmutableList<Activity> items;

  public ActivityList(Activity... items) {
    this(ImmutableList.copyOf(items));
  }

  @JsonCreator
  public ActivityList(@JsonProperty("items") List<Activity> items) {
    this.items = (items == null) ? ImmutableList.<Activity>of() : ImmutableList.copyOf(items);
  }

  public ImmutableList<Activity> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ActivityList)) return false;

    ActivityList that = (ActivityList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ActivityList{" +
        "items=" + items +
        '}';
  }
}
