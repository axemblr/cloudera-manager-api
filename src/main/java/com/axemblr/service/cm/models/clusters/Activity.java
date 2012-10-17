/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.clusters;

import static com.google.common.base.Preconditions.checkNotNull;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents a user activity, such as a MapReduce job, a Hive query, an Oozie workflow, etc.
 * <p/>
 * http://cloudera.github.com/cm_api/apidocs/v1/el_ns0_activity.html
 * http://cloudera.github.com/cm_api/apidocs/v1/ns0_apiActivity.html
 */
public class Activity {

  private final String name;
  private final ActivityType type;
  private final String parent;
  private final long startTime;
  private final long finishTime;
  private final String id;
  private final ActivityStatus status;
  private final String user;
  private final String group;
  private final String inputDir;
  private final String outputDir;
  private final String mapper;
  private final String combiner;
  private final String reducer;
  private final String queueName;
  private final String schedulerPriority;

  @JsonCreator
  public Activity(@JsonProperty("name") String name,
                  @JsonProperty("type") ActivityType type,
                  @JsonProperty("parent") String parent,
                  @JsonProperty("startTime") long startTime,
                  @JsonProperty("finishTime") long finishTime,
                  @JsonProperty("id") String id,
                  @JsonProperty("status") ActivityStatus status,
                  @JsonProperty("user") String user,
                  @JsonProperty("group") String group,
                  @JsonProperty("inputDir") String inputDir,
                  @JsonProperty("outputDir") String outputDir,
                  @JsonProperty("mapper") String mapper,
                  @JsonProperty("combiner") String combiner,
                  @JsonProperty("reducer") String reducer,
                  @JsonProperty("queueName") String queueName,
                  @JsonProperty("schedulerPriority") String schedulerPriority) {
    this.id = checkNotNull(id);
    this.name = checkNotNull(name);
    this.type = type;
    this.parent = parent;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.status = status;
    this.user = user;
    this.group = group;
    this.inputDir = inputDir;
    this.outputDir = outputDir;
    this.mapper = mapper;
    this.combiner = combiner;
    this.reducer = reducer;
    this.queueName = queueName;
    this.schedulerPriority = schedulerPriority;
  }

  /**
   * Activity name.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Activity type. Whether it's an MR job, a Pig job, a Hive query, etc.
   *
   * @return
   */
  public ActivityType getType() {
    return type;
  }

  /**
   * The name of the parent activity.
   *
   * @return
   */
  public String getParent() {
    return parent;
  }

  /**
   * The start time of this activity.
   *
   * @return
   */
  public long getStartTime() {
    return startTime;
  }

  /**
   * The finish time of this activity.
   *
   * @return
   */
  public long getFinishTime() {
    return finishTime;
  }

  /**
   * Activity id, which is unique within a MapReduce service.
   *
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * Activity status.
   *
   * @return
   */
  public ActivityStatus getStatus() {
    return status;
  }

  /**
   * The user who submitted this activity.
   *
   * @return
   */
  public String getUser() {
    return user;
  }

  /**
   * The user-group of this activity.
   *
   * @return
   */
  public String getGroup() {
    return group;
  }

  /**
   * The input data directory of the activity. An HDFS url.
   *
   * @return
   */
  public String getInputDir() {
    return inputDir;
  }

  /**
   * The output result directory of the activity. An HDFS url.
   *
   * @return
   */
  public String getOutputDir() {
    return outputDir;
  }

  /**
   * The mapper class.
   *
   * @return
   */
  public String getMapper() {
    return mapper;
  }

  /**
   * The combiner class.
   *
   * @return
   */
  public String getCombiner() {
    return combiner;
  }

  /**
   * The reducer class.
   *
   * @return
   */
  public String getReducer() {
    return reducer;
  }

  /**
   * The scheduler queue this activity is in.
   *
   * @return
   */
  public String getQueueName() {
    return queueName;
  }

  /**
   * The scheduler priority of this activity.
   *
   * @return
   */
  public String getSchedulerPriority() {
    return schedulerPriority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Activity)) return false;

    Activity activity = (Activity) o;

    if (finishTime != activity.finishTime) return false;
    if (startTime != activity.startTime) return false;
    if (combiner != null ? !combiner.equals(activity.combiner) : activity.combiner != null) return false;
    if (group != null ? !group.equals(activity.group) : activity.group != null) return false;
    if (id != null ? !id.equals(activity.id) : activity.id != null) return false;
    if (inputDir != null ? !inputDir.equals(activity.inputDir) : activity.inputDir != null) return false;
    if (mapper != null ? !mapper.equals(activity.mapper) : activity.mapper != null) return false;
    if (name != null ? !name.equals(activity.name) : activity.name != null) return false;
    if (outputDir != null ? !outputDir.equals(activity.outputDir) : activity.outputDir != null) return false;
    if (parent != null ? !parent.equals(activity.parent) : activity.parent != null) return false;
    if (queueName != null ? !queueName.equals(activity.queueName) : activity.queueName != null) return false;
    if (reducer != null ? !reducer.equals(activity.reducer) : activity.reducer != null) return false;
    if (schedulerPriority != null ? !schedulerPriority.equals(activity.schedulerPriority) : activity.schedulerPriority != null)
      return false;
    if (status != activity.status) return false;
    if (type != activity.type) return false;
    if (user != null ? !user.equals(activity.user) : activity.user != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (parent != null ? parent.hashCode() : 0);
    result = 31 * result + (int) (startTime ^ (startTime >>> 32));
    result = 31 * result + (int) (finishTime ^ (finishTime >>> 32));
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (group != null ? group.hashCode() : 0);
    result = 31 * result + (inputDir != null ? inputDir.hashCode() : 0);
    result = 31 * result + (outputDir != null ? outputDir.hashCode() : 0);
    result = 31 * result + (mapper != null ? mapper.hashCode() : 0);
    result = 31 * result + (combiner != null ? combiner.hashCode() : 0);
    result = 31 * result + (reducer != null ? reducer.hashCode() : 0);
    result = 31 * result + (queueName != null ? queueName.hashCode() : 0);
    result = 31 * result + (schedulerPriority != null ? schedulerPriority.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Activity{" +
        "name='" + name + '\'' +
        ", type=" + type +
        ", parent='" + parent + '\'' +
        ", startTime=" + startTime +
        ", finishTime=" + finishTime +
        ", id='" + id + '\'' +
        ", status=" + status +
        ", user='" + user + '\'' +
        ", group='" + group + '\'' +
        ", inputDir='" + inputDir + '\'' +
        ", outputDir='" + outputDir + '\'' +
        ", mapper='" + mapper + '\'' +
        ", combiner='" + combiner + '\'' +
        ", reducer='" + reducer + '\'' +
        ", queueName='" + queueName + '\'' +
        ", schedulerPriority='" + schedulerPriority + '\'' +
        '}';
  }
}
