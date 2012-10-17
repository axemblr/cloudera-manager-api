/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models;

import com.axemblr.service.cm.SerializationTest;
import com.axemblr.service.cm.models.commands.Command;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.io.File;

public class CommandClassesSerializationTest extends SerializationTest {

  private final DateTime expectedStartTime = new DateTime("2012-05-06T20:56:58.190Z", DateTimeZone.UTC);
  private final DateTime expectedEndTime = new DateTime("2012-05-06T20:57:27.171Z", DateTimeZone.UTC);

  @Test
  public void testCommand() throws Exception {
    Command command = mapper.readValue(new File("src/test/resources/command.json"), Command.class);
    assertEquals(1, command.getId());
    assertEquals("restart", command.getName());
    assertEquals(expectedStartTime, command.getStartTime());
    assertEquals(expectedEndTime, command.getEndTime());
    assertEquals("/some/url", command.getResultDataUrl());
    assertFalse(command.isSuccess());
    assertFalse(command.isSuccess());
    assertEquals(0, command.getChildren().getItems().size());
    assertEquals(2, command.getParent().getId());
  }
}
