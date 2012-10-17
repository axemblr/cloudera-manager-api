/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.hosts;

import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * TOOD add javadoc
 */
public class HostRef {
  private final String hostId;

  @JsonCreator
  public HostRef(@JsonProperty("hostId") String hostId) {
    this.hostId = checkNotNull(hostId);
  }

  public String getHostId() {
    return hostId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HostRef)) return false;

    HostRef hostRef = (HostRef) o;

    if (hostId != null ? !hostId.equals(hostRef.hostId) : hostRef.hostId != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return hostId != null ? hostId.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "HostRef{" +
        "hostId='" + hostId + '\'' +
        '}';
  }
}
