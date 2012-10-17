/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

/**
 * Available Service Types in Cloudera Manager. Service types from both CDH3 and CDH4 are mixed in.
 * <p/>
 * A service is an abstract entity providing a capability in a cluster. Examples of services are HDFS, MapReduce,
 * YARN, and HBase. A service is usually distributed, and contains a set of roles that physically run on the cluster.
 * A service has its own configuration, status, metrics, and roles. You may issue commands against a service, or
 * against a set of roles in bulk. Additionally, an HDFS service has nameservices, and a MapReduce service has activities.
 * <p/>
 * All services belong to a cluster (except for the Cloudera Management Service), and is uniquely identified by its
 * name within a Cloudera Manager installation. The types of services available depends on the CDH version of the cluster.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__clusters_-clusterName-_services.html
 */
public enum ServiceType {
  /**
   * Available in: CDH4 and CDH3.
   */
  HDFS,
  /**
   * Available in: CDH4 and CDH3.
   */
  MAPREDUCE,
  /**
   * Available in: CDH4 and CDH3.
   */
  HBASE,
  /**
   * Available in: CDH4 and CDH3.
   */
  OOZIE,
  /**
   * Available in: CDH4 and CDH3.
   */
  ZOOKEEPER,
  /**
   * Available in: CDH4 and CDH3.
   */
  HUE,
  /**
   * Available in: CDH4.
   */
  YARN
}
