/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.tools;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The echoMessage carries a message to be echoed back from the API service.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_echoMessage.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiEcho.html
 */
public class EchoMessage {
  private final String message;

  public EchoMessage() {
    this("Hello wold");
  }

  @JsonCreator
  public EchoMessage(@JsonProperty("message") String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EchoMessage)) return false;

    EchoMessage message1 = (EchoMessage) o;

    if (message != null ? !message.equals(message1.message) : message1.message != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return message != null ? message.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "EchoMessage{" +
        "message='" + message + '\'' +
        '}';
  }
}
