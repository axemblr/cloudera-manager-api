/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

/**
 * High availability status.
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_haStatus.html
 */
public enum HaStatus {
  ACTIVE,
  STANDBY,
  UNKNOWN;
}
