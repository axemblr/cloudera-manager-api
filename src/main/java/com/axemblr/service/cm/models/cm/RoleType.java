/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

/**
 * Available RoleTypes for Cloudera Manager services.
 * Both CDH3 and CDH4 roles are mixed here.
 * <p/>
 * Role type refers to the class that a role belongs to. For example, an HBase service has the Master role type and the
 * RegionServer role type. Different service types have different sets of role types. This is not to be confused with
 * a role, which refers to a specific role instance that is physically assigned to a host.
 * <p/>
 * You can specify configuration for a role type, which is inherited by all role instances of that type.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/tutorial.html
 */
public enum RoleType {
  /**
   * Service: HDFS (CDH3 and CDH4).
   */
  NAMENODE,
  /**
   * Service: HDFS (CDH3 and CDH4).
   */
  DATANODE,
  /**
   * Service: HDFS (CDH3 and CDH4).
   */
  SECONDARYNAMENODE,
  /**
   * Service: HDFS (CDH3 and CDH4).
   */
  BALANCER,
  /**
   * Service: HDFS (CDH3 and CDH4), MAPREDUCE, HBASE, YARN.
   */
  GATEWAY,
  /**
   * Service HDFS (CDH4).
   */
  HTTPFS,
  /**
   * Service HDFS (CDH4).
   */
  FAILOVERCONTROLLER,
  /**
   * Service HDFS (CDH4).
   */
  JOBTRACKER,
  /**
   * Service MAPREDUCE.
   */
  TASKTRACKER,
  /**
   * Service MAPREDUCE.
   */
  MASTER,
  /**
   * Service HBASE.
   */
  REGIONSERVER,
  /**
   * Service HBASE.
   */
  RESROUCEMANAGER,
  /**
   * Service YARN.
   */
  NODEMANAGER,
  /**
   * Service YARN.
   */
  JOBHISTORY,
  /**
   * Service OOZIE.
   */
  OOZIE_SERVER,
  /**
   * Service ZOOKEEPER.
   */
  SERVER,
  /**
   * Service HUE (CDH3).
   */
  JOBSUBD,
  /**
   * Service HUE (CDH3 and CDH4).
   */
  HUE_SERVER,
  /**
   * Service HUE (CDH3 and CDH4).
   */
  BEESWAX_SERVER,
  /**
   * Service HUE (CDH3 and CDH4).
   */
  KT_RENEWER
}
