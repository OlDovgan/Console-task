package com.example.testcontainers.config;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class ContainersEnvironment {

  protected static PostgreSQLContainer container;

  static {
    container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14.5")
        .withInitScript("Table.sql");
    container.start();
  }
}



