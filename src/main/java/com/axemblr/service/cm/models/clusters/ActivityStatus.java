/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

/**
 * Possible Activity Statuses.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiActivityType.html
 */
public enum ActivityStatus {
  UNKNOWN,
  SUBMITTED,
  STARTED,
  SUSPENDED,
  FAILED,
  KILLED,
  SUCCEED,
  ASSUMED_SUCCEEDED
}
