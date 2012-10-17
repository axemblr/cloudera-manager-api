/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

/**
 * Represents the configured run state of a service.
 * User: ieugen
 * Date: 27.07.2012
 * Time: 01:52
 */
public enum ServiceState {
  HISTORY_NOT_AVAILABLE,
  UNKNOWN,
  STARTING,
  STARTED,
  STOPPING,
  STOPPED;
}
