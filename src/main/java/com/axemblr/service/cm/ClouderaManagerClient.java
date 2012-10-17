/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm;

import com.axemblr.service.cm.apis.Clusters;
import com.axemblr.service.cm.apis.Commands;
import com.axemblr.service.cm.apis.Hosts;
import com.axemblr.service.cm.apis.Tools;
import com.axemblr.service.cm.apis.impl.DefaultClusters;
import com.axemblr.service.cm.apis.impl.DefaultCommands;
import com.axemblr.service.cm.apis.impl.DefaultHosts;
import com.axemblr.service.cm.apis.impl.DefaultTools;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import static com.google.common.base.Preconditions.checkNotNull;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ext.JodaDeserializers;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.net.URI;

/**
 * Creates a client used to make requests to the server.
 */
public class ClouderaManagerClient {

  private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 10 * 1000;  // TODO make configurable

  private final Client client;
  private final WebResource baseResource;
  private final Tools toolsApi;
  private final Clusters clustersApi;


  ClouderaManagerClient(URI connectionURI, String user, String password) {
    this.client = setupJerseyClient(user, password);
    this.baseResource = client.resource(checkNotNull(connectionURI));
    this.toolsApi = new DefaultTools(this.baseResource);
    this.clustersApi = new DefaultClusters(this.baseResource);
  }

  protected Client setupJerseyClient(String user, String password) {
    ClientConfig clientConfig = new DefaultClientConfig();
    ObjectMapper mapper = makeJacksonObjectMapper();
    clientConfig.getSingletons().add(new JacksonJsonProvider(mapper));
    Client client1 = Client.create(clientConfig);
    client1.addFilter(new HTTPBasicAuthFilter(user, password));
    client1.addFilter(new ClientFilter() {
      @Override
      public ClientResponse handle(ClientRequest cr) throws ClientHandlerException {
        cr.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        return getNext().handle(cr);
      }
    });
    client1.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT_MS);
    return client1;
  }

  protected ObjectMapper makeJacksonObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationConfig(mapper.getSerializationConfig()
        .without(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS));
    mapper.registerModule(makeJodaTimeModule());
    mapper.registerModule(new GuavaModule());
    return mapper;
  }

  protected SimpleModule makeJodaTimeModule() {
    SimpleModule jodaTimeModule = new SimpleModule("ISODateTimeModule", new Version(1, 0, 0, null));
    jodaTimeModule.addDeserializer(DateTime.class, new JodaDeserializers.DateTimeDeserializer<DateTime>(DateTime.class));
    jodaTimeModule.addSerializer(new JodaSerializers.DateTimeSerializer());
    return jodaTimeModule;
  }

  public Tools tools() {
    return toolsApi;
  }

  public Clusters clusters() {
    return clustersApi;
  }

  public Commands commands() {
    return new DefaultCommands(baseResource);
  }

  public Hosts hosts() {
    return new DefaultHosts(baseResource);
  }

  public void enableHttpLogging(boolean logEnabled) {
    if (logEnabled) {
      client.addFilter(new LoggingFilter());
    } else {
      client.removeFilter(new LoggingFilter());
    }
  }

  public static ClouderaManagerBuilder withConnectionString(String connectionString) {
    return new ClouderaManagerBuilder(connectionString);
  }

  public static ClouderaManagerBuilder withConnectionURI(URI uri) {
    return new ClouderaManagerBuilder(uri);
  }

  public static class ClouderaManagerBuilder {

    private URI connectionURI;
    private String user;
    private String password;

    private ClouderaManagerBuilder(String connectionString) {
      this(URI.create(connectionString));
    }

    private ClouderaManagerBuilder(URI connectionURI) {
      this.connectionURI = checkNotNull(connectionURI);
    }

    public ClouderaManagerBuilder withAuth(String user, String password) {
      this.user = checkNotNull(user);
      this.password = checkNotNull(password);
      return this;
    }

    public ClouderaManagerClient build() {
      return new ClouderaManagerClient(connectionURI, user, password);
    }
  }
}
