/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.Events;
import com.axemblr.service.cm.models.events.Event;
import com.axemblr.service.cm.models.events.EventQueryResult;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.WebResource;

/**
 * Cloudera Manager /events API implementation.
 */
public class DefaultEvents implements Events {

  private final String mountPoint;
  private WebResource resource;

  public DefaultEvents(WebResource resource) {
    this("/api/v1/events", resource);
  }

  public DefaultEvents(String mountPoint, WebResource resource) {
    this.mountPoint = checkNotNull(mountPoint);
    this.resource = resource.path(mountPoint);
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public EventQueryResult queryEvents(int maxResults, int resultOffset, String query) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Override
  public Event getEventById(String eventId) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
}
