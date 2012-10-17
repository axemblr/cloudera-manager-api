/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

/**
 * Represents of the high-level health status of a subject in the cluster.
 */
public enum HealthSummary {
  DISABLED,
  HISTORY_NOT_AVAILABLE,
  NOT_AVAILABLE,
  GOOD,
  CONCERNING,
  BAD;
}
