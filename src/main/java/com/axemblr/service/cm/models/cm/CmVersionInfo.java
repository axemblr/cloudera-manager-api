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
 * Version information of Cloudera Manager itself.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_cmVersionInfo.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiVersionInfo.html
 */
public class CmVersionInfo {

  private final String version;
  private final boolean snapshot;
  private final String buildUser;
  private final long buildTimestamp;
  private final String gitHash;

  @JsonCreator
  public CmVersionInfo(
      @JsonProperty("version") String version,
      @JsonProperty("snapshot") boolean snapshot,
      @JsonProperty("buildUser") String buildUser,
      @JsonProperty("buildTimestamp") long buildTimestamp,
      @JsonProperty("gitHash") String gitHash) {
    this.version = version;
    this.snapshot = snapshot;
    this.buildUser = buildUser;
    this.buildTimestamp = buildTimestamp;
    this.gitHash = gitHash;
  }

  /**
   * Version.
   *
   * @return
   */
  public String getVersion() {
    return version;
  }

  /**
   * Whether this build is a development snapshot.
   *
   * @return
   */
  public boolean isSnapshot() {
    return snapshot;
  }

  /**
   * The user performing the build.
   *
   * @return
   */
  public String getBuildUser() {
    return buildUser;
  }

  /**
   * Build timestamp.
   *
   * @return
   */
  public long getBuildTimestamp() {
    return buildTimestamp;
  }

  /**
   * Source control management hash.
   *
   * @return
   */
  public String getGitHash() {
    return gitHash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CmVersionInfo)) return false;

    CmVersionInfo that = (CmVersionInfo) o;

    if (buildTimestamp != that.buildTimestamp) return false;
    if (snapshot != that.snapshot) return false;
    if (buildUser != null ? !buildUser.equals(that.buildUser) : that.buildUser != null) return false;
    if (gitHash != null ? !gitHash.equals(that.gitHash) : that.gitHash != null) return false;
    if (version != null ? !version.equals(that.version) : that.version != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = version != null ? version.hashCode() : 0;
    result = 31 * result + (snapshot ? 1 : 0);
    result = 31 * result + (buildUser != null ? buildUser.hashCode() : 0);
    result = 31 * result + (int) (buildTimestamp ^ (buildTimestamp >>> 32));
    result = 31 * result + (gitHash != null ? gitHash.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CmVersionInfo{" +
        "version='" + version + '\'' +
        ", snapshot=" + snapshot +
        ", buildUser='" + buildUser + '\'' +
        ", buildTimestamp='" + buildTimestamp + '\'' +
        ", gitHash='" + gitHash + '\'' +
        '}';
  }
}
