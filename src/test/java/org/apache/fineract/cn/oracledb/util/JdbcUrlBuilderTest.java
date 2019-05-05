/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.cn.oracledb.util;

import org.junit.Assert;
import org.junit.Test;

public class JdbcUrlBuilderTest {

  public JdbcUrlBuilderTest() {
    super();
  }

  @Test
  public void shouldCreateOracleUrl() {
    final String expectedJdbcUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521)))(CONNECT_DATA=(SID=ORCLCDB)(SERVER=DEDICATED)))";

    final String oracleDbJdbcUrl = JdbcUrlBuilder
        .create(JdbcUrlBuilder.DatabaseType.ORACLEDB)
        .host("localhost")
        .port("1521")
        .instanceName("ORCLCDB")
        .build();
    System.out.println("ORACLE URL" + oracleDbJdbcUrl);
    Assert.assertEquals(expectedJdbcUrl, oracleDbJdbcUrl);
  }

  @Test
  public void shouldCreateOracleUrlNoInstance() {
    final String expectedJdbcUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521)))(CONNECT_DATA=(SERVER=DEDICATED)))";

    final String oracleDbJdbcUrl = JdbcUrlBuilder
        .create(JdbcUrlBuilder.DatabaseType.ORACLEDB)
        .host("localhost")
        .port("1521").build();
    System.out.println("ORACLE URL" + oracleDbJdbcUrl);
    Assert.assertEquals(expectedJdbcUrl, oracleDbJdbcUrl);
  }

  @Test
  public void shouldCreateOracleReplicationUrl() {
    final String expectedJdbcUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=" +
      "(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))"+
      "(ADDRESS=(PROTOCOL=TCP)(HOST=anotherhost)(PORT=1521))"+
      "(FAILOVER=on)(LOAD_BALANCE=ON))"+
      "(CONNECT_DATA=(SERVER=DEDICATED)(SID=ORCLCDB)))";

    final String oracleDbJdbcUrl = JdbcUrlBuilder
        .create(JdbcUrlBuilder.DatabaseType.ORACLEDB)
        .host("localhost, anotherhost")
        .port("1521")
        .instanceName("ORCLCDB")
        .build();
    System.out.println("ORACLE URL" + oracleDbJdbcUrl);
    Assert.assertEquals(expectedJdbcUrl, oracleDbJdbcUrl);
  }
}