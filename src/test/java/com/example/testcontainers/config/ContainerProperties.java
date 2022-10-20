package com.example.testcontainers.config;

import java.util.Properties;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerProperties {

   public Properties getProperties(PostgreSQLContainer container) {
    Properties properties = new Properties();
    properties.setProperty("db-url", container.getJdbcUrl());
    properties.setProperty("db-username", container.getUsername());
    properties.setProperty("db-password", container.getPassword());
    return properties;
  }

}
