/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.hosts;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * TODO add javadoc
 */
public class HostList {
  private ImmutableSet<Host> items;

  @JsonCreator
  public HostList(@JsonProperty("items") Set<Host> items) {
    this.items = (items == null) ? ImmutableSet.<Host>of() : ImmutableSet.copyOf(items);
  }

  public ImmutableSet<Host> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "HostList{" +
        "items=" + items +
        '}';
  }
}
