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
 * Arguments used for the HDFS disable HA command.
 */
public class HdfsDisableHaArguments {
  private final String activeName;
  private final String secondaryName;
  private final boolean startDependentServices;
  private final boolean deployClientConfigs;

  @JsonCreator
  public HdfsDisableHaArguments(@JsonProperty("activeName") String activeName,
                                @JsonProperty("secondaryName") String secondaryName,
                                @JsonProperty("startDependentServices") boolean startDependentServices,
                                @JsonProperty("deployClientConfigs") boolean deployClientConfigs) {
    this.activeName = activeName;
    this.secondaryName = secondaryName;
    this.startDependentServices = startDependentServices;
    this.deployClientConfigs = deployClientConfigs;
  }

  /**
   * Name of the the NameNode to be kept.
   *
   * @return
   */
  public String getActiveName() {
    return activeName;
  }

  /**
   * Name of the SecondaryNamenode to associate with the active NameNode.
   *
   * @return
   */
  public String getSecondaryName() {
    return secondaryName;
  }

  /**
   * Whether to re-start dependent services. Defaults to true.
   *
   * @return
   */
  public boolean shouldRestartDependentServices() {
    return startDependentServices;
  }

  /**
   * Whether to re-deploy client configurations. Defaults to true.
   *
   * @return
   */
  public boolean shouldRedeployClientConfigs() {
    return deployClientConfigs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HdfsDisableHaArguments)) return false;

    HdfsDisableHaArguments that = (HdfsDisableHaArguments) o;

    if (deployClientConfigs != that.deployClientConfigs) return false;
    if (startDependentServices != that.startDependentServices) return false;
    if (activeName != null ? !activeName.equals(that.activeName) : that.activeName != null) return false;
    if (secondaryName != null ? !secondaryName.equals(that.secondaryName) : that.secondaryName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = activeName != null ? activeName.hashCode() : 0;
    result = 31 * result + (secondaryName != null ? secondaryName.hashCode() : 0);
    result = 31 * result + (startDependentServices ? 1 : 0);
    result = 31 * result + (deployClientConfigs ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "HdfsDisableHaArguments{" +
        "activeName='" + activeName + '\'' +
        ", secondaryName='" + secondaryName + '\'' +
        ", startDependentServices=" + startDependentServices +
        ", deployClientConfigs=" + deployClientConfigs +
        '}';
  }
}
