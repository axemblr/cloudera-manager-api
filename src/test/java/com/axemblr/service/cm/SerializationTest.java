/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm;


import com.axemblr.service.cm.ClouderaManagerClient;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Base class for JSON serialization/de-serialization.
 * We use the {@link ObjectMapper} from the client.
 */
public class SerializationTest {

  protected final ObjectMapper mapper;
  protected static final ClouderaManagerClient client = ClouderaManagerClient
      .withConnectionString("http://localhost:7180").build();

  public SerializationTest() {
    this(client.makeJacksonObjectMapper());
  }

  public SerializationTest(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public static String quoted(String date) {
    return String.format(" \"%s\"", date);
  }
}
