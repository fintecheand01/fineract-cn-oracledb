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
package com.mx.fintecheando.fineract.cn.oracledb.config;


import com.jolbox.bonecp.BoneCPDataSource;
import com.mx.fintecheando.fineract.cn.oracledb.domain.FlywayFactoryBean;
import com.mx.fintecheando.fineract.cn.oracledb.util.JdbcUrlBuilder;
import com.mx.fintecheando.fineract.cn.oracledb.util.OracleDBConstants;
import org.apache.fineract.cn.lang.ApplicationName;
import org.apache.fineract.cn.lang.config.EnableApplicationName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SuppressWarnings("WeakerAccess")
@Configuration
@ConditionalOnProperty(prefix = "oracledb", name = "enabled", matchIfMissing = true)
@EnableTransactionManagement
@EnableApplicationName
public class OracleDBJavaConfiguration {

  private final Environment env;

  @Autowired
  public OracleDBJavaConfiguration(final Environment env) {
    super();
    this.env = env;
  }

  @Bean(name = OracleDBConstants.LOGGER_NAME)
  public Logger logger() {
    return LoggerFactory.getLogger(OracleDBConstants.LOGGER_NAME);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setPersistenceUnitName("metaPU");
    em.setDataSource(dataSource);
    em.setPackagesToScan("org.apache.fineract.cn.**.repository");

    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());

    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public FlywayFactoryBean flywayFactoryBean(final ApplicationName applicationName) {
    return new FlywayFactoryBean(applicationName);
  }

  @Bean
  public MetaDataSourceWrapper metaDataSourceWrapper() {

    final BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
    boneCPDataSource.setDriverClass(
            this.env.getProperty(OracleDBConstants.ORACLEDB_DRIVER_CLASS_PROP, OracleDBConstants.ORACLEDB_DRIVER_CLASS_DEFAULT));
    boneCPDataSource.setJdbcUrl(JdbcUrlBuilder
            .create(JdbcUrlBuilder.DatabaseType.ORACLEDB)
            .host(this.env.getProperty(OracleDBConstants.ORACLEDB_HOST_PROP, OracleDBConstants.ORACLEDB_HOST_DEFAULT))
            .port(this.env.getProperty(OracleDBConstants.ORACLEDB_PORT_PROP, OracleDBConstants.ORACLEDB_PORT_DEFAULT))
            .instanceName(this.env.getProperty(OracleDBConstants.ORACLEDB_DATABASE_NAME_PROP, OracleDBConstants.ORACLEDB_DATABASE_NAME_DEFAULT))
            .build());
    boneCPDataSource.setUsername(
            this.env.getProperty(OracleDBConstants.ORACLEDB_USER_PROP, OracleDBConstants.ORACLEDB_USER_DEFAULT));
    boneCPDataSource.setPassword(
            this.env.getProperty(OracleDBConstants.ORACLEDB_PASSWORD_PROP, OracleDBConstants.ORACLEDB_PASSWORD_DEFAULT));
    boneCPDataSource.setIdleConnectionTestPeriodInMinutes(
            Long.valueOf(this.env.getProperty(OracleDBConstants.BONECP_IDLE_CONNECTION_TEST_PROP, OracleDBConstants.BONECP_IDLE_CONNECTION_TEST_DEFAULT)));
    boneCPDataSource.setIdleMaxAgeInMinutes(
            Long.valueOf(this.env.getProperty(OracleDBConstants.BONECP_IDLE_MAX_AGE_PROP, OracleDBConstants.BONECP_IDLE_MAX_AGE_DEFAULT)));
    boneCPDataSource.setMaxConnectionsPerPartition(
            Integer.valueOf(this.env.getProperty(OracleDBConstants.BONECP_MAX_CONNECTION_PARTITION_PROP, OracleDBConstants.BONECP_MAX_CONNECTION_PARTITION_DEFAULT)));
    boneCPDataSource.setMinConnectionsPerPartition(
            Integer.valueOf(this.env.getProperty(OracleDBConstants.BONECP_MIN_CONNECTION_PARTITION_PROP, OracleDBConstants.BONECP_MIN_CONNECTION_PARTITION_DEFAULT)));
    boneCPDataSource.setPartitionCount(
            Integer.valueOf(this.env.getProperty(OracleDBConstants.BONECP_PARTITION_COUNT_PROP, OracleDBConstants.BONECP_PARTITION_COUNT_DEFAULT)));
    boneCPDataSource.setAcquireIncrement(
            Integer.valueOf(this.env.getProperty(OracleDBConstants.BONECP_ACQUIRE_INCREMENT_PROP, OracleDBConstants.BONECP_ACQUIRE_INCREMENT_DEFAULT)));
    boneCPDataSource.setStatementsCacheSize(
            Integer.valueOf(this.env.getProperty(OracleDBConstants.BONECP_STATEMENT_CACHE_PROP, OracleDBConstants.BONECP_STATEMENT_CACHE_DEFAULT)));

    final Properties driverProperties = new Properties();
    driverProperties.setProperty("useServerPrepStmts", "false");
    boneCPDataSource.setDriverProperties(driverProperties);
    return new MetaDataSourceWrapper(boneCPDataSource);
  }

  private Properties additionalProperties() {
    final Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
    return properties;
  }
}
