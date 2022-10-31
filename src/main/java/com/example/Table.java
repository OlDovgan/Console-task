package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Table {

  private final FileReader file = new FileReader();
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public Table(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void create(String fileName)
      throws IOException, URISyntaxException {
    for (String strSql : String.join(" ", file.readFile(fileName)).split(";")) {
      jdbcTemplate.execute(strSql + ";");
    }
  }
}
