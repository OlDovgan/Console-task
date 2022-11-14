package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Table {
@Autowired
  private FileReader file ;
  @Autowired
  private  JdbcTemplate jdbcTemplate;

  public void create(String fileName)
      throws IOException, URISyntaxException {
    for (String strSql : String.join(" ", file.readFile(fileName)).split(";")) {
      jdbcTemplate.execute(strSql + ";");
    }
  }
}
