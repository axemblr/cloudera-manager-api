/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models;

import com.axemblr.service.cm.SerializationTest;
import com.axemblr.service.cm.models.hosts.Metric;
import com.axemblr.service.cm.models.hosts.MetricData;
import com.axemblr.service.cm.models.hosts.MetricList;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.File;

/**
 * Test serialization/de-serialization for /hosts classes.
 */
public class HostsClassesSerializationTest extends SerializationTest {

  private final String TIME1 = "2012-05-06T20:56:57.918Z";
  private final DateTime expectedDateTime = new DateTime(TIME1, DateTimeZone.UTC);

  @Test
  public void testMetricData() throws Exception {
    MetricData data = mapper.readValue(new File("src/test/resources/metric-data.json"), MetricData.class);
    assertEquals(12345, data.getTimestamp());
    assertEquals(9991.5, data.getValue(), 0.01);
  }

  @Test
  public void testMetric() throws Exception {
    Metric metric = mapper.readValue(new File("src/test/resources/metric.json"), Metric.class);
    assertEquals("metric-01", metric.getName());
    assertEquals("cluster-01", metric.getContext());
    assertEquals(1, metric.getData().size());
    MetricData actualData = metric.getData().get(0);
    MetricData expectedData = new MetricData(12345, 9991.5);
    assertEquals(expectedData, actualData);
    assertEquals("LaLa Metric", metric.getDescription());
    assertEquals("Metric la la la", metric.getDisplayName());
  }

  @Test
  public void testMetricList() throws Exception {
    MetricList list = mapper.readValue(new File("src/test/resources/metric-list.json"), MetricList.class);
    assertEquals(1, list.getItems().size());
    Metric actual = list.getItems().get(0);
    Metric expected = new Metric("metric-01", "cluster-01", "second", ImmutableList.of(new MetricData(12345, 9991.5))
        , "Metric la la la", "LaLa Metric");
    assertEquals(expected, actual);
  }

  @Test
  public void testDateTimeDeserialization() throws Exception {
    final String jsonDate = quoted(TIME1);
    final DateTime actual = mapper.readValue(jsonDate, DateTime.class);
    assertEquals(expectedDateTime, actual);
  }

  @Test
  public void testDateTimeSerialization() throws Exception {
    final String date = CharMatcher.anyOf("\"").removeFrom(mapper.writeValueAsString(expectedDateTime));
    assertEquals(TIME1, date);
    final DateTime dt2 = mapper.readValue(quoted(date), DateTime.class);
    assertEquals(expectedDateTime.toInstant(), dt2.toInstant());
  }
}
