/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Information about the Cloudera Manager license.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_license.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiLicense.html
 */
public class License {
  private final String owner;
  private final String uuid;
  private final long expirationDate;

  @JsonCreator
  public License(@JsonProperty("owner") String owner,
                 @JsonProperty("uuid") String uuid,
                 @JsonProperty("expiration") long expirationDate) {
    this.owner = owner;
    this.uuid = uuid;
    this.expirationDate = expirationDate;
  }

  /**
   * The owner (organization name) of the license.
   *
   * @return
   */
  public String getOwner() {
    return owner;
  }

  /**
   * A UUID of this license.
   *
   * @return
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * The expiration date.
   *
   * @return
   */
  public long getExpirationDate() {
    return expirationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof License)) return false;

    License license = (License) o;

    if (expirationDate != license.expirationDate) return false;
    if (owner != null ? !owner.equals(license.owner) : license.owner != null) return false;
    if (uuid != null ? !uuid.equals(license.uuid) : license.uuid != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = owner != null ? owner.hashCode() : 0;
    result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
    result = 31 * result + (int) (expirationDate ^ (expirationDate >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "License{" +
        "owner='" + owner + '\'' +
        ", uuid='" + uuid + '\'' +
        ", expirationDate=" + expirationDate +
        '}';
  }
}
