/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.cm;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Arguments used for the collectDiagnosticData command.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_collectDiagnosticDataArgs.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiCollectDiagnosticDataArguments.html
 */
public class CollectDiagnosticDataArguments {
  private final String startTime;
  private final String endTime;
  private final boolean includeInfoLog;
  private final String ticketNumber;
  private final String comments;

  @JsonCreator
  public CollectDiagnosticDataArguments(@JsonProperty("startTime") String startTime,
                                        @JsonProperty("endTime") String endTime,
                                        @JsonProperty("includeInfoLog") boolean includeInfoLog,
                                        @JsonProperty("ticketNumber") String ticketNumber,
                                        @JsonProperty("comments") String comments) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.includeInfoLog = includeInfoLog;
    this.ticketNumber = ticketNumber;
    this.comments = comments;
  }

  /**
   * The start time (in ISO 8601 format) of the period to collection statistics for.
   *
   * @return
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * The end time (in ISO 8601 format) of the period to collection statistics for.
   *
   * @return
   */
  public String getEndTime() {
    return endTime;
  }

  /**
   * Whether to include INFO level logs.
   * <p/>
   * WARN, ERROR, and FATAL level logs are always included.
   *
   * @return
   */
  public boolean isIncludeInfoLog() {
    return includeInfoLog;
  }

  /**
   * The support ticket number to attach to this data collection.
   *
   * @return
   */
  public String getTicketNumber() {
    return ticketNumber;
  }

  /**
   * Comments to include with this data collection.
   *
   * @return
   */
  public String getComments() {
    return comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CollectDiagnosticDataArguments)) return false;

    CollectDiagnosticDataArguments that = (CollectDiagnosticDataArguments) o;

    if (includeInfoLog != that.includeInfoLog) return false;
    if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
    if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
    if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
    if (ticketNumber != null ? !ticketNumber.equals(that.ticketNumber) : that.ticketNumber != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = startTime != null ? startTime.hashCode() : 0;
    result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
    result = 31 * result + (includeInfoLog ? 1 : 0);
    result = 31 * result + (ticketNumber != null ? ticketNumber.hashCode() : 0);
    result = 31 * result + (comments != null ? comments.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CollectDiagnosticDataArguments{" +
        "startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", includeInfoLog=" + includeInfoLog +
        ", ticketNumber='" + ticketNumber + '\'' +
        ", comments='" + comments + '\'' +
        '}';
  }
}
