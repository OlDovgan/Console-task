package com.example.testcontainers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

@Component
public class ContainersEnvironment {

  @Autowired
  protected final static PostgreSQLContainer container;

  static {
    container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14.5")
        .withInitScript("Table.sql");
    container.start();
  }
}



