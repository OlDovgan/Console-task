package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {

  private final FileReader file = new FileReader();
  private final JDBC jdbc;

  public Table(JDBC jdbc) {
    this.jdbc = jdbc;
  }

  public void create(String fileName)
      throws IOException, URISyntaxException, SQLException {
    try (Connection connection = jdbc.getDbConnection();
        Statement statement = connection.createStatement()) {
      for (String strSql : String.join(" ", file.readFile(fileName)).split(";")) {
        statement.executeUpdate(strSql + ";");
      }
    }
  }
}
