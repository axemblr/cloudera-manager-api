/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.cm.CollectDiagnosticDataArguments;
import com.axemblr.service.cm.models.commands.Command;
import com.axemblr.service.cm.models.commands.CommandList;

/**
 * Cloudera Manager API for /cm/commands
 */
public interface ClouderaManagementCommands {

  /**
   * List active global commands.
   *
   * @return A list of active global commands.
   */
  public CommandList getActiveCommands();

  /**
   * Collect diagnostic data from hosts managed by Cloudera Manager. After the command has completed, the ApiCommand
   * will contain a resultDataUrl from where you can download the result.
   *
   * @param args The command arguments.
   * @return Detailed command information.
   */
  public Command collectDiagnosticData(CollectDiagnosticDataArguments args);

  /**
   * Generate missing Kerberos credentials. This command will affect all services that have been configured to
   * use Kerberos, and haven't had their credentials generated yet.
   *
   * @return Information about the submitted command.
   */
  public Command generateCredentials();

  /**
   * Runs the host inspector on the configured hosts.
   *
   * @return Information about the submitted command.
   */
  public Command inspectHosts();
}
