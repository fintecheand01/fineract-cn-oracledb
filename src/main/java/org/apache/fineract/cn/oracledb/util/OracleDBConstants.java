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

public interface OracleDBConstants {

  String LOGGER_NAME = "oracledb-logger";

  String ORACLEDB_DRIVER_CLASS_PROP = "oracledb.driverClass";
  String ORACLEDB_DRIVER_CLASS_DEFAULT = "oracle.jdbc.driver.OracleDriver";
  String ORACLEDB_DATABASE_NAME_PROP = "oracledb.database";
  String ORACLEDB_DATABASE_NAME_DEFAULT = "seshat";
  String ORACLEDB_HOST_PROP = "oracledb.host";
  String ORACLEDB_HOST_DEFAULT = "localhost";
  String ORACLEDB_PORT_PROP = "oracledb.port";
  String ORACLEDB_PORT_DEFAULT = "1521";
  String ORACLEDB_USER_PROP = "oracledb.user";
  String ORACLEDB_USER_DEFAULT = "sys";
  String ORACLEDB_PASSWORD_PROP = "oracledb.password";
  String ORACLEDB_PASSWORD_DEFAULT = "oracle";

  String BONECP_IDLE_MAX_AGE_PROP = "bonecp.idleMaxAgeInMinutes";
  String BONECP_IDLE_MAX_AGE_DEFAULT = "240";
  String BONECP_IDLE_CONNECTION_TEST_PROP = "bonecp.idleConnectionTestPeriodInMinutes";
  String BONECP_IDLE_CONNECTION_TEST_DEFAULT = "60";
  String BONECP_MAX_CONNECTION_PARTITION_PROP = "bonecp.maxConnectionsPerPartition";
  String BONECP_MAX_CONNECTION_PARTITION_DEFAULT = "16";
  String BONECP_MIN_CONNECTION_PARTITION_PROP = "bonecp.minConnectionsPerPartition";
  String BONECP_MIN_CONNECTION_PARTITION_DEFAULT = "4";
  String BONECP_PARTITION_COUNT_PROP = "bonecp.partitionCount";
  String BONECP_PARTITION_COUNT_DEFAULT = "2";
  String BONECP_ACQUIRE_INCREMENT_PROP = "bonecp.acquireIncrement";
  String BONECP_ACQUIRE_INCREMENT_DEFAULT = "4";
  String BONECP_STATEMENT_CACHE_PROP = "bonecp.statementsCacheSize";
  String BONECP_STATEMENT_CACHE_DEFAULT = "128";
}
