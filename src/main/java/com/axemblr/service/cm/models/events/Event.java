/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models.events;

import com.google.common.collect.ImmutableSet;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

/**
 * Events model noteworthy incidents in Cloudera Manager or the managed Hadoop cluster.
 * An event carries its event category, severity, and a string content. They also have generic attributes,
 * which are free-form key value pairs. Important events may be promoted into alerts.
 * <p/>
 */
public class Event {
  private final String id;
  private final String content;
  private final long timeOccurred;
  private final long timeReceived;
  private final EventCategory category;
  private final EventSeverity severity;
  private final boolean alert;
  private final ImmutableSet<EventAttribute> attributes;

  @JsonCreator
  public Event(
      @JsonProperty("id") String id,
      @JsonProperty("content") String content,
      @JsonProperty("timeOccurred") long timeOccurred,
      @JsonProperty("timeReceived") long timeReceived,
      @JsonProperty("category") EventCategory category,
      @JsonProperty("severity") EventSeverity severity,
      @JsonProperty("alert") boolean alert,
      @JsonProperty("attributes") Set<EventAttribute> attributes) {
    this.id = id;
    this.content = content;
    this.timeOccurred = timeOccurred;
    this.timeReceived = timeReceived;
    this.category = category;
    this.severity = severity;
    this.alert = alert;
    this.attributes = (attributes == null) ? ImmutableSet.<EventAttribute>of() : ImmutableSet.copyOf(attributes);
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public long getTimeOccurred() {
    return timeOccurred;
  }

  public long getTimeReceived() {
    return timeReceived;
  }

  public EventCategory getCategory() {
    return category;
  }

  public EventSeverity getSeverity() {
    return severity;
  }

  public boolean isAlert() {
    return alert;
  }

  public ImmutableSet<EventAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Event)) return false;

    Event event = (Event) o;

    if (id != null ? !id.equals(event.id) : event.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Event{" +
        "id='" + id + '\'' +
        ", content='" + content + '\'' +
        ", timeOccurred=" + timeOccurred +
        ", timeReceived=" + timeReceived +
        ", category=" + category +
        ", severity=" + severity +
        ", alert=" + alert +
        ", attributes=" + attributes +
        '}';
  }
}
