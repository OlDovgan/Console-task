package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC {

  private final Properties properties;

  public JDBC(Properties properties) {
    this.properties = properties;
  }

  public Connection getDbConnection()
      throws SQLException {
    return DriverManager.getConnection(
        properties.getProperty("db-url"),
        properties.getProperty("db-username"),
        properties.getProperty("db-password"));
  }

}
