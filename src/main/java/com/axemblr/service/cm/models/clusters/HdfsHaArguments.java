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
 * Arguments used for HDFS HA commands.
 */
public class HdfsHaArguments {
  private final String activeName;
  private final String activeSharedEditsPath;
  private final String standByName;
  private final String standBySharedEditsPath;
  private final String nameService;
  private final boolean startDependentServices;
  private final boolean deployClientConfigs;

  @JsonCreator
  public HdfsHaArguments(@JsonProperty("activeName") String activeName,
                         @JsonProperty("activeSharedEditsPath") String activeSharedEditsPath,
                         @JsonProperty("standByName") String standByName,
                         @JsonProperty("standBySharedEditsPath") String standBySharedEditsPath,
                         @JsonProperty("nameservice") String nameService,
                         @JsonProperty("startDependentServices") boolean startDependentServices,
                         @JsonProperty("deployClientConfigs") boolean deployClientConfigs) {
    this.activeName = activeName;
    this.activeSharedEditsPath = activeSharedEditsPath;
    this.standByName = standByName;
    this.standBySharedEditsPath = standBySharedEditsPath;
    this.nameService = nameService;
    this.startDependentServices = startDependentServices;
    this.deployClientConfigs = deployClientConfigs;
  }

  /**
   * Name of the active NameNode.
   *
   * @return
   */
  public String getActiveName() {
    return activeName;
  }

  /**
   * Path to the shared edits directory on the active NameNode's host.
   *
   * @return
   */
  public String getActiveSharedEditsPath() {
    return activeSharedEditsPath;
  }

  /**
   * Name of the stand-by Namenode.
   *
   * @return
   */
  public String getStandByName() {
    return standByName;
  }

  /**
   * Path to the shared edits directory on the stand-by NameNode's host.
   *
   * @return
   */
  public String getStandBySharedEditsPath() {
    return standBySharedEditsPath;
  }

  /**
   * NameService that identifies the HA pair.
   *
   * @return
   */
  public String getNameService() {
    return nameService;
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
    if (!(o instanceof HdfsHaArguments)) return false;

    HdfsHaArguments that = (HdfsHaArguments) o;

    if (deployClientConfigs != that.deployClientConfigs) return false;
    if (startDependentServices != that.startDependentServices) return false;
    if (activeName != null ? !activeName.equals(that.activeName) : that.activeName != null) return false;
    if (activeSharedEditsPath != null ? !activeSharedEditsPath.equals(that.activeSharedEditsPath) : that.activeSharedEditsPath != null)
      return false;
    if (nameService != null ? !nameService.equals(that.nameService) : that.nameService != null) return false;
    if (standByName != null ? !standByName.equals(that.standByName) : that.standByName != null) return false;
    if (standBySharedEditsPath != null ? !standBySharedEditsPath.equals(that.standBySharedEditsPath) : that.standBySharedEditsPath != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = activeName != null ? activeName.hashCode() : 0;
    result = 31 * result + (activeSharedEditsPath != null ? activeSharedEditsPath.hashCode() : 0);
    result = 31 * result + (standByName != null ? standByName.hashCode() : 0);
    result = 31 * result + (standBySharedEditsPath != null ? standBySharedEditsPath.hashCode() : 0);
    result = 31 * result + (nameService != null ? nameService.hashCode() : 0);
    result = 31 * result + (startDependentServices ? 1 : 0);
    result = 31 * result + (deployClientConfigs ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "HdfsHaArguments{" +
        "activeName='" + activeName + '\'' +
        ", activeSharedEditsPath='" + activeSharedEditsPath + '\'' +
        ", standByName='" + standByName + '\'' +
        ", standBySharedEditsPath='" + standBySharedEditsPath + '\'' +
        ", nameService='" + nameService + '\'' +
        ", startDependentServices=" + startDependentServices +
        ", deployClientConfigs=" + deployClientConfigs +
        '}';
  }
}
