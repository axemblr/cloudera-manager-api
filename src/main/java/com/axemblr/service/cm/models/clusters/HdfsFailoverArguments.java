/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Arguments used when enabling HDFS automatic failover.
 */
public class HdfsFailoverArguments {
  private final String nameservice;
  private final ServiceRef zooKeeperService;
  private final String activeFCName;
  private final String standByFCName;

  @JsonCreator
  public HdfsFailoverArguments(@JsonProperty("nameservice") String nameservice,
                               @JsonProperty("zooKeeperService") ServiceRef zooKeeperService,
                               @JsonProperty("activeFCName") String activeFCName,
                               @JsonProperty("standByFCName") String standByFCName) {
    this.nameservice = nameservice;
    this.zooKeeperService = zooKeeperService;
    this.activeFCName = activeFCName;
    this.standByFCName = standByFCName;
  }

  /**
   * NameService for which to enable automatic failover.
   *
   * @return
   */
  public String getNameservice() {
    return nameservice;
  }

  /**
   * The ZooKeeper service to use.
   *
   * @return
   */
  public ServiceRef getZooKeeperService() {
    return zooKeeperService;
  }

  /**
   * Name of the failover controller to create for the active NameNode.
   *
   * @return
   */
  public String getActiveFCName() {
    return activeFCName;
  }

  /**
   * Name of the failover controller to create for the stand-by NameNode.
   *
   * @return
   */
  public String getStandByFCName() {
    return standByFCName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HdfsFailoverArguments)) return false;

    HdfsFailoverArguments that = (HdfsFailoverArguments) o;

    if (activeFCName != null ? !activeFCName.equals(that.activeFCName) : that.activeFCName != null) return false;
    if (nameservice != null ? !nameservice.equals(that.nameservice) : that.nameservice != null) return false;
    if (standByFCName != null ? !standByFCName.equals(that.standByFCName) : that.standByFCName != null) return false;
    if (zooKeeperService != null ? !zooKeeperService.equals(that.zooKeeperService) : that.zooKeeperService != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = nameservice != null ? nameservice.hashCode() : 0;
    result = 31 * result + (zooKeeperService != null ? zooKeeperService.hashCode() : 0);
    result = 31 * result + (activeFCName != null ? activeFCName.hashCode() : 0);
    result = 31 * result + (standByFCName != null ? standByFCName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "HdfsFailoverArguments{" +
        "nameservice='" + nameservice + '\'' +
        ", zooKeeperService=" + zooKeeperService +
        ", activeFCName='" + activeFCName + '\'' +
        ", standByFCName='" + standByFCName + '\'' +
        '}';
  }
}
