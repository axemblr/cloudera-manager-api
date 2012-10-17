/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.commands;

import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A list of commands.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_commandList.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiCommandList.html
 */
public class CommandList {

  private final ImmutableList<Command> items;

  @JsonCreator
  public CommandList(@JsonProperty("items") List<Command> items) {
    this.items = (items == null) ? ImmutableList.<Command>of() : ImmutableList.copyOf(items);
  }

  public ImmutableList<Command> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CommandList)) return false;

    CommandList that = (CommandList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "CommandList{" +
        "items=" + items +
        '}';
  }
}
