/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.tools.EchoMessage;
import com.axemblr.service.cm.models.tools.ErrorMessage;

/**
 * Cloudera Manager Tools API.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__tools_echo.html
 */
public interface Tools {

  /**
   * Echoes the provided message back to the caller.
   *
   * @param message The message to echo back. Default: 'Hello, World!'
   * @return The original message.
   */
  public EchoMessage echo(String message);

  /**
   * Throws an error containing the given input message. This is what an error response looks like.
   * {
   * "message": "An error message",
   * "causes": [ "A list of causes", "Potentially null" ]
   * }
   * The message field contains a description of the error. The causes field, if not null, contains a list of causes
   * for the error.
   * <p/>
   * Note that this never returns an echoMessage. Instead, the result (and all error results) has the above structure.
   *
   * @param message The error message to echo. Default:	'Default error message'
   * @return Will always be an exception.
   */
  public ErrorMessage echoError(String message);
}
