/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.tools;

import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Error message returned by Cloudera Manager.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__tools_echoError.html
 */
public class ErrorMessage {
  private final String message;
  private final ImmutableList<String> causes;

  @JsonCreator
  public ErrorMessage(@JsonProperty("message") String message,
                      @JsonProperty("causes") List<String> causes) {
    this.message = message;
    this.causes = (causes == null) ? ImmutableList.<String>of() : ImmutableList.copyOf(causes);
  }

  /**
   * The echoMessage carries a message to be echoed back from the API service.
   * Will always be an exception.
   *
   * @return
   */
  public String getMessage() {
    return message;
  }

  public ImmutableList<String> getCauses() {
    return causes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ErrorMessage)) return false;

    ErrorMessage error = (ErrorMessage) o;

    if (causes != null ? !causes.equals(error.causes) : error.causes != null) return false;
    if (message != null ? !message.equals(error.message) : error.message != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = message != null ? message.hashCode() : 0;
    result = 31 * result + (causes != null ? causes.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ErrorMessage{" +
        "message='" + message + '\'' +
        ", causes=" + causes +
        '}';
  }
}
