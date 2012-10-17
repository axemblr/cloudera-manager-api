/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A list of configuration data.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiConfigList.html
 */
public class ConfigList {
  private final ImmutableList<Config> items;

  public ConfigList(Config... items) {
    this(ImmutableList.copyOf(items));
  }

  @JsonCreator
  public ConfigList(@JsonProperty("items") List<Config> items) {
    this.items = (items == null) ? ImmutableList.<Config>of() : ImmutableList.copyOf(items);
  }

  public ImmutableList<Config> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ConfigList)) return false;

    ConfigList that = (ConfigList) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ConfigList{" +
        "items=" + items +
        '}';
  }
}
