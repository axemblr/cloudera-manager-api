/*
 * Copyright S.C. Axemblr Software Solutions S.R.L. (c) 2012.
 *
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code package.
 */

package com.axemblr.service.cm.models;

import com.axemblr.service.cm.SerializationTest;
import com.axemblr.service.cm.models.clusters.HaStatus;
import com.axemblr.service.cm.models.clusters.HealthSummary;
import com.axemblr.service.cm.models.cm.CmVersionInfo;
import com.axemblr.service.cm.models.cm.CollectDiagnosticDataArguments;
import com.axemblr.service.cm.models.cm.Config;
import com.axemblr.service.cm.models.cm.License;
import com.axemblr.service.cm.models.cm.Role;
import com.axemblr.service.cm.models.cm.RoleList;
import com.axemblr.service.cm.models.cm.RoleNameList;
import com.axemblr.service.cm.models.cm.RoleState;
import com.axemblr.service.cm.models.cm.RoleType;
import com.axemblr.service.cm.models.cm.RoleTypeList;
import com.axemblr.service.cm.models.cm.Service;
import com.axemblr.service.cm.models.cm.ServiceList;
import com.axemblr.service.cm.models.cm.ServiceState;
import com.axemblr.service.cm.models.cm.ServiceType;
import com.axemblr.service.cm.models.cm.ValidationState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.io.File;

/**
 * Unit tests for JSON serialization/de-serialization of com.axemblr.cm.models.cm package.
 */
public class CmClasesSerializationTest extends SerializationTest {

  @Test
  public void testVersionInfo() throws Exception {
    CmVersionInfo info = mapper.readValue(new File("src/test/resources/version-info.json"), CmVersionInfo.class);
    assertEquals("1.2.3", info.getVersion());
    assertFalse(info.isSnapshot());
    assertEquals("ieugen", info.getBuildUser());
    assertEquals(1223, info.getBuildTimestamp());
    assertEquals("60a5b6e6ff29cdc54701c4ee8d998f2a4b93ce55", info.getGitHash());
  }

  @Test
  public void testCollectDiagnosticDataArguments() throws Exception {
    CollectDiagnosticDataArguments arguments = mapper.readValue(
        new File("src/test/resources/collect-diagnostic-data-args.json"),
        CollectDiagnosticDataArguments.class);
    assertEquals("1994-11-05T13:15:30Z", arguments.getStartTime());
    assertEquals("1994-11-05T13:16:30Z", arguments.getEndTime());
    assertFalse(arguments.isIncludeInfoLog());
    assertEquals("1", arguments.getTicketNumber());
    assertEquals("bogus information", arguments.getComments());
  }

  @Test
  public void testConfig() throws Exception {
    Config config = mapper.readValue(new File("src/test/resources/config.json"), Config.class);
    assertEquals("provider", config.getName());
    assertEquals("axemblr.com", config.getValue());
    assertFalse(config.isRequired());
    assertEquals("Axemblr.com", config.getDisplayName());
    assertEquals("We deliver Hadoop in the cloud.", config.getDescription());
    assertEquals("related", config.getRelatedName());
    assertEquals(ValidationState.OK, config.getValidationState());
    assertEquals("validation message", config.getValidationMessage());
  }

  @Test
  public void testLicense() throws Exception {
    License license = mapper.readValue(new File("src/test/resources/license.json"), License.class);
    assertEquals("Axemblr", license.getOwner());
    assertEquals("1", license.getUuid());
    assertEquals(123, license.getExpirationDate());
  }

  @Test
  public void testRoleNameList() throws Exception {
    RoleNameList roles = mapper.readValue(new File("src/test/resources/role-name-list.json"), RoleNameList.class);
    assertEquals(2, roles.getItems().size());
    assertEquals("role1", roles.getItems().asList().get(1));
  }

  @Test
  public void testRoleAndRoleList() throws Exception {
    RoleList roleList = mapper.readValue(new File("src/test/resources/role-list.json"), RoleList.class);
    assertEquals(1, roleList.getItems().size());
    Role role = roleList.getItems().asList().get(0);
    assertEquals("role-01", role.getName());
    assertEquals(RoleType.NAMENODE, role.getType());
    assertEquals(HealthSummary.DISABLED, role.getHealthSummary());
    assertEquals(HaStatus.ACTIVE, role.getHaStatus());
    assertEquals(RoleState.HISTORY_NOT_AVAILABLE, role.getRoleState());
    assertEquals("/role/1", role.getRoleUrl());
  }

  @Test
  public void testRoleTypeList() throws Exception {
    //same json representation
    RoleTypeList roleTypes = mapper.readValue(new File("src/test/resources/role-type-list.json"), RoleTypeList.class);
    assertEquals(2, roleTypes.getItems().size());
    assertThat(roleTypes.getItems().asList(), hasItem(RoleType.MASTER));
    assertThat(roleTypes.getItems().asList(), hasItem(RoleType.NAMENODE));
  }

  @Test
  public void testServiceAndServiceList() throws Exception {
    ServiceList serviceList = mapper.readValue(new File("src/test/resources/service-list.json"), ServiceList.class);
    assertEquals(1, serviceList.getItems().size());
    Service service = serviceList.getItems().asList().get(0);
    assertEquals("service-01", service.getName());
    assertEquals(ServiceType.MAPREDUCE, service.getType());
    assertEquals(ServiceState.HISTORY_NOT_AVAILABLE, service.getServiceState());
    assertEquals(HealthSummary.DISABLED, service.getHealthSummary());
    assertEquals(0, service.getHealthChecks().size());
    assertEquals("/service/url", service.getServiceUrl());
    assertEquals("cluster-01", service.getClusterRef().getClusterName());
  }
}
