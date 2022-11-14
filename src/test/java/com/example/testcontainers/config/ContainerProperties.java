package com.example.testcontainers.config;

import static com.example.testcontainers.config.ContainersEnvironment.container;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerProperties {

  public final DataSource getDataSource(PostgreSQLContainer container) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(container.getDriverClassName());
    dataSource.setUrl(container.getJdbcUrl());
    dataSource.setUsername(container.getUsername());
    dataSource.setPassword(container.getPassword());
    return dataSource;
  }
}
