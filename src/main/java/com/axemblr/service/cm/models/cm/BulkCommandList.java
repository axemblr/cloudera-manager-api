/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;
import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A list of commands.
 * This list is returned whenever commands are issued in bulk, and contains a second list
 * with information about errors issuing specific commands.
 */
public class BulkCommandList extends CommandList {

  private ImmutableList<String> errors;

  @JsonCreator
  public BulkCommandList(@JsonProperty("items") List<Command> items,
                         @JsonProperty("error") List<String> errors) {
    super(items);
    this.errors = (errors == null) ? ImmutableList.<String>of() : ImmutableList.copyOf(errors);
  }

  public ImmutableList<String> getErrors() {
    return errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BulkCommandList)) return false;
    if (!super.equals(o)) return false;

    BulkCommandList that = (BulkCommandList) o;

    if (errors != null ? !errors.equals(that.errors) : that.errors != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (errors != null ? errors.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BulkCommandList{" +
        "errors=" + errors + ",\n" +
        "items=" + this.getItems() +
        '}';
  }
}
