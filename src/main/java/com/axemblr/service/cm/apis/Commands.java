/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.cm.BulkCommandList;
import com.axemblr.service.cm.models.commands.Command;

import java.util.List;

/**
 * Cloudera Manager Commands API.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/path__commands_-commandId-.html
 * http://cloudera.github.com/cm_api/apidocs/v1/path__commands_-commandId-_abort.html
 */
public interface Commands {

  /**
   * Retrieve detailed information about a command.
   *
   * @param commandId The command id.
   * @return Detailed command information.
   */
  public Command details(long commandId);

  /**
   * Retrieve detailed information about a command.
   *
   * @param command the command for which to fetch details
   * @return Detailed command information.
   */
  public Command details(Command command);

  /**
   * Abort a running command.
   *
   * @param commandId The command id.
   * @return Detailed command information.
   */
  public Command abort(long commandId);

  /**
   * Abort a running command.
   *
   * @param command The command to abort.
   * @return Detailed command information.
   */
  public Command abort(Command command);

  /**
   * Blocks until Cloudera Manager finishes executing the specified command.
   * Queries the server at regular intervals to find out the command state.
   *
   * @param command command to await execution
   * @return the finished command details
   * @throws InterruptedException
   */
  public Command waitFor(Command command) throws InterruptedException;

  /**
   * Wait for all commands in the list to finish execution.
   *
   * @param commands
   * @return
   * @throws InterruptedException
   */
  public List<Command> waitFor(List<Command> commands) throws InterruptedException;

  /**
   * Wait for all commands in the list to finish execution.
   *
   * @param commandList
   * @return
   * @throws InterruptedException
   */
  public List<Command> waitFor(BulkCommandList commandList) throws InterruptedException;

  /**
   * Specifies how long the client must wait between {@link Command} detail requests done by
   * {@link Commands#waitFor(com.axemblr.service.cm.models.commands.Command)}
   *
   * @param seconds number of seconds to wait between pooling
   * @return the {@link Commands } object for chaining
   */
  public Commands withQueryInterval(int seconds);

}
