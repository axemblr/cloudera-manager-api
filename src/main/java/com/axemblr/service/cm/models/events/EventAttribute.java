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
 * Key values pair.
 */
public class EventAttribute {
  private final String name;
  private final ImmutableSet<String> values;

  @JsonCreator
  public EventAttribute(@JsonProperty("name") String name,
                        @JsonProperty("values") Set<String> values) {
    this.name = name;
    this.values = (values == null) ? ImmutableSet.<String>of() : ImmutableSet.copyOf(values);
  }

  public String getName() {
    return name;
  }

  public ImmutableSet<String> getValues() {
    return values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EventAttribute)) return false;

    EventAttribute that = (EventAttribute) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (values != null ? !values.equals(that.values) : that.values != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (values != null ? values.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EventAttribute{" +
        "name='" + name + '\'' +
        ", values=" + values +
        '}';
  }
}
