/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.apis;

import com.axemblr.service.cm.models.events.Event;
import com.axemblr.service.cm.models.events.EventQueryResult;

/**
 * Cloudera Manager /events API.
 */
public interface Events {

  /**
   * Allows you to query events in the system.
   * <p/>
   * The query to perform to find events in the system. It accepts querying the intersection of a list of constraints,
   * joined together with semicolons (without spaces).
   * <p/>
   * For example:<br/>
   * <ul>
   * <li><strong>alert==true</strong> looks for alerts.</li>
   * <li><strong>alert==true;attributes.host!=flaky.mysite.com</strong> looks for alerts, but exclude those with the
   * host attribute of "flaky.mysite.com".</li>
   * <li><strong>category==log_event;attributes.log_level==ERROR</strong> looks for error log events. Event attribute
   * matching is case sensitive.</li>
   * <li><strong>attributes.service==hbase1;content==hlog</strong> looks for any events from the "hbase1" service
   * that mention "hlog". </li>
   * <li><strong> attributes.service==hbase1;content!=hlog </strong> looks for any events from the "hbase1" service
   * that do not mention "hlog".  A query must not contain only negative constraints (!=).
   * It returns empty results because there is nothing to perform exclusion on. </li>
   * <li><strong> attributes.role_type==NAMENODE;severity==critical important </strong> looks for any important or
   * critical events related to all NameNodes. </li>
   * <li><strong> severity==critical;timeReceived=ge=2012-05-04T00:00;timeReceived=lt=2012-05-04T00:10 </strong> looks
   * for critical events received between the given 10 minute range. </li>
   * </ul>
   * When polling for events, use timeReceived instead of timeOccurred because events arrive out of order.
   * You may query any fields present in the ApiEvent object. You can also query by event attribute values using
   * the attributes.* syntax. Values for date time fields (e.g. timeOccurred, timeReceived) should be ISO8601 timestamps.
   * The other valid comparators are =lt=, =le=, =ge=, and =gt=. They stand for "<", "<=", ">=", ">" respectively.
   * These comparators are only applicable for date time fields.
   *
   * @param maxResults   The maximum number of events to return.
   * @param resultOffset Specified the offset of events to return.
   * @param query
   * @return The results of the query
   */
  public EventQueryResult queryEvents(int maxResults, int resultOffset, String query);

  /**
   * Returns a specific event in the system.
   *
   * @param eventId The UUID of the event to read
   * @return The Event object with the specified UUID
   */
  public Event getEventById(String eventId);

}
