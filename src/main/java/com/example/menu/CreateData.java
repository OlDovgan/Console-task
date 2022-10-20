package com.example.menu;


import com.example.Data;
import com.example.JDBC;
import com.example.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Random;

public class CreateData implements Menu {

  private final JDBC jdbc;

  public CreateData(JDBC jdbc) {
    this.jdbc = jdbc;
  }

  public String getItemName() {
    return "Create new data ";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
      new Table(jdbc).create("Table.sql");
      System.out.println("Tables created successfully");
      System.out.println("Please wait...");
      new Data(new Random()).createAll(jdbc);
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (SQLException | URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
