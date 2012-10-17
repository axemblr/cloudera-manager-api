/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.cm.CmVersionInfo;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.ConfigList;
import com.axemblr.service.cm.models.cm.License;

import java.util.List;

/**
 * Cloudera Manager /cm API.
 * <p/>
 * Everything related to the operation of Cloudera Manager is available under the /cm resource.
 * This includes global commands, system configuration, and the Cloudera Management Service.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public interface ClouderaManager {

  /**
   * Provides version information of Cloudera Manager itself.
   *
   * @return Version information.
   */
  public CmVersionInfo getVersionInfo();

  /**
   * Returns the entire contents of the Cloudera Manager log file.
   *
   * @return
   */
  public String getLogFile();

  /**
   * Retrieve information about the Cloudera Manager license.
   *
   * @return The current license information.
   */
  public License getLicense();

  /**
   * Updates the Cloudera Manager license. After a new license is installed, the Cloudera Manager needs to be restarted
   * for the changes to take effect. The license file should be uploaded using a request with content type
   * "multipart/form-data", instead of being encoded into a JSON representation.
   *
   * @param multipartLicense
   * @return The new license information.
   */
  public License updateLicense(String multipartLicense);

  /**
   * Retrieve the Cloudera Manager settings.
   *
   * @return The current Cloudera Manager settings.
   */
  public ConfigList getConfiguration();

  /**
   * Update the Cloudera Manager settings. If a value is set in the given configuration, it will be added to the
   * manager's settings, replacing any existing entry. If a value is unset (its value is null), the existing the
   * setting will be erased. Settings that are not listed in the input will maintain their current values.
   *
   * @param newConfig Settings to update.
   * @return The updated configuration.
   */
  public ConfigList updateConfig(List<Config> newConfig);

  /**
   * Get the API client for /cm/service path.
   */
  public ClouderaManagementService getService();

}
