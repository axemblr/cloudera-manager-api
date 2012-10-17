/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

/**
 * Possible activity types.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiActivityStatus.html
 */
public enum ActivityType {
  UNKNOWN,
  OOZIE,
  PIG,
  HIVE,
  MR,
  STREAMING;
}
