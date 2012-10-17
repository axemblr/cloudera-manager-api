/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

/**
 * Represents the configured run state of a role.
 */
public enum RoleState {
  HISTORY_NOT_AVAILABLE,
  UNKNOWN,
  STARTING,
  STARTED,
  BUSY,
  STOPPING,
  STOPPED;
}
