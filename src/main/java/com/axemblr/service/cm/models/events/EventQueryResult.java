/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.events;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * The result of a query for events.
 */
public class EventQueryResult {
  private final long totalResults;
  private final ImmutableSet<Event> items;

  @JsonCreator
  public EventQueryResult(@JsonProperty("totalResults") long totalResults,
                          @JsonProperty("items") Set<Event> items) {
    this.totalResults = totalResults;
    this.items = (items == null) ? ImmutableSet.<Event>of() : ImmutableSet.copyOf(items);
  }

  public long getTotalResults() {
    return totalResults;
  }

  public ImmutableSet<Event> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EventQueryResult)) return false;

    EventQueryResult that = (EventQueryResult) o;

    if (totalResults != that.totalResults) return false;
    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (totalResults ^ (totalResults >>> 32));
    result = 31 * result + (items != null ? items.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EventQueryResult{" +
        "totalResults=" + totalResults +
        ", items=" + items +
        '}';
  }
}
