/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.commands;

import com.axemblr.service.cm.models.clusters.ServiceRef;
import com.axemblr.service.cm.models.cm.RoleRef;
import com.axemblr.service.cm.models.hosts.HostRef;
import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/**
 * Provides detailed information about a submitted command.
 * <p/>
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_command.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiCommand.html
 */
public class Command {

  private final long id;
  private final String name;
  private final DateTime startTime;
  private final DateTime endTime;
  private final boolean active;
  private final boolean success;
  private final String resultMessage;
  private final String resultDataUrl;
  private final ServiceRef serviceRef;
  private final RoleRef roleRef;
  private final HostRef hostRef;
  private final Command parent;
  private final CommandList children;

  @JsonCreator
  public Command(@JsonProperty("id") long id,
                 @JsonProperty("name") String name,
                 @JsonProperty("startTime") DateTime startTime,
                 @JsonProperty("endTime") DateTime endTime,
                 @JsonProperty("active") boolean active,
                 @JsonProperty("success") boolean success,
                 @JsonProperty("resultMessage") String resultMessage,
                 @JsonProperty("resultDataUrl") String resultDataUrl,
                 @JsonProperty("serviceRef") ServiceRef serviceRef,
                 @JsonProperty("roleRef") RoleRef roleRef,
                 @JsonProperty("hostRef") HostRef hostRef,
                 @JsonProperty("parent") Command parent,
                 @JsonProperty("children") CommandList children) {
    this.id = checkNotNull(id);
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.active = active;
    this.success = success;
    this.resultMessage = resultMessage;
    this.resultDataUrl = resultDataUrl;
    this.serviceRef = serviceRef;
    this.roleRef = roleRef;
    this.hostRef = hostRef;
    this.parent = parent;
    this.children = children;
  }

  /**
   * The command ID.
   *
   * @return
   */
  public long getId() {
    return id;
  }

  /**
   * The command name.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * The start time.
   *
   * @return
   */
  public DateTime getStartTime() {
    return startTime;
  }

  /**
   * The end time, if the command is finished.
   *
   * @return
   */
  public DateTime getEndTime() {
    return endTime;
  }

  /**
   * Whether the command is currently active.
   *
   * @return
   */
  public boolean isActive() {
    return active;
  }

  /**
   * If the command is finished, whether it was successful.
   *
   * @return
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * If the command is finished, the result message.
   *
   * @return
   */
  public String getResultMessage() {
    return resultMessage;
  }

  /**
   * URL to the command's downloadable result data, if any exists.
   *
   * @return
   */
  public String getResultDataUrl() {
    return resultDataUrl;
  }

  /**
   * Reference to the service (for service commands only).
   *
   * @return
   */
  public ServiceRef getServiceRef() {
    return serviceRef;
  }

  /**
   * Reference to the role (for role commands only).
   *
   * @return
   */
  public RoleRef getRoleRef() {
    return roleRef;
  }

  /**
   * Reference to the host (for host commands only).
   *
   * @return
   */
  public HostRef getHostRef() {
    return hostRef;
  }

  /**
   * Reference to the parent command, if any.
   *
   * @return
   */
  public Command getParent() {
    return parent;
  }

  /**
   * List of child commands. Only available in the full view. The list contains only the summary view of the children.
   *
   * @return
   */
  public CommandList getChildren() {
    return children;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Command)) return false;

    Command command = (Command) o;

    if (id != command.id) return false;
    if (name != null ? !name.equals(command.name) : command.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Command{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", active=" + active +
        ", success=" + success +
        ", resultMessage='" + resultMessage + '\'' +
        ", resultDataUrl='" + resultDataUrl + '\'' +
        ", serviceRef=" + serviceRef +
        ", roleRef=" + roleRef +
        ", hostRef=" + hostRef +
        ", parent=" + parent +
        ", children=" + children +
        '}';
  }
}
