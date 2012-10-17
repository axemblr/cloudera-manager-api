/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis.impl;

import com.axemblr.service.cm.apis.Commands;
import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.commands.Command;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.WebResource;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Cloudera Manager Commands API implementation.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__commands_-commandId-.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__commands_-commandId-_abort.html
 */
public class DefaultCommands implements Commands {

  public static final int DEFAULT_RETRY_INTERVAL = 1;

  private final String mountPoint;
  private final WebResource resource;
  private int retryInterval;

  public DefaultCommands(WebResource resource) {
    this("/api/v1/commands", resource);
  }

  public DefaultCommands(String mountPoint, WebResource resource) {
    this.mountPoint = checkNotNull(mountPoint);
    this.resource = checkNotNull(resource).path(mountPoint);
    this.retryInterval = DEFAULT_RETRY_INTERVAL;
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public WebResource getResource() {
    return resource;
  }

  @Override
  public Command details(long commandId) {
    return resource.path(Long.toString(commandId)).get(Command.class);
  }

  @Override
  public Command abort(long commandId) {
    return resource.path(Long.toString(commandId)).path("abort").post(Command.class);
  }

  @Override
  public Command abort(Command command) {
    return abort(command);
  }

  @Override
  public Command details(Command command) {
    return details(command.getId());
  }

  @Override
  public Command waitFor(Command command) throws InterruptedException {
    Command response = details(command);
    while (response.isActive()) {
      TimeUnit.SECONDS.sleep(getRetryInterval());
      response = details(command);
    }
    return response;
  }

  @Override
  public List<Command> waitFor(List<Command> commands) throws InterruptedException {
    List<Command> finishedCommands = Lists.newArrayList();
    for (Command cmd : commands) {
      finishedCommands.add(waitFor(cmd));
    }
    return ImmutableList.copyOf(finishedCommands);
  }

  @Override
  public List<Command> waitFor(BulkCommandList commandList) throws InterruptedException {
    // Warning: be aware or recursive call !
    return waitFor(commandList.getItems());
  }

  @Override
  public Commands withQueryInterval(int seconds) {
    checkArgument(seconds > 0, "Retry interval must be larger than 0 seconds");
    this.retryInterval = seconds;
    return this;
  }

  public int getRetryInterval() {
    return retryInterval;
  }
}
