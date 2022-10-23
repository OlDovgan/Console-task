package com.example.testcontainers.config;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class ContainersEnvironment {

  protected final static PostgreSQLContainer container;

  static {
    container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14.5")
        .withInitScript("Table.sql");
    container.start();
  }
}



