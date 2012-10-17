/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.Tools;
import com.axemblr.service.cm.models.tools.EchoMessage;
import com.axemblr.service.cm.models.tools.ErrorMessage;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

/**
 * Default implementation for Cloudera Manager tools API.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__tools_echo.html
 */
public class DefaultTools implements Tools {

  private final String mountPoint;
  private final WebResource resource;

  public DefaultTools(WebResource resource) {
    this("/api/v1/tools", resource);
  }

  public DefaultTools(String mountPoint, WebResource resource) {
    this.mountPoint = checkNotNull(mountPoint);
    this.resource = checkNotNull(resource).path(mountPoint);
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public EchoMessage echo(String message) {
    return resource.path("/echo")
        .queryParam("message", message)
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get(EchoMessage.class);
  }

  @Override
  public ErrorMessage echoError(String message) {
    ErrorMessage error = resource.path("/echoError")
        .queryParam("message", message)
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .get(ErrorMessage.class);
    return error;
  }
}
