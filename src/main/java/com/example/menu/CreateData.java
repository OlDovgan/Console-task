package com.example.menu;

import static com.example.spring_boot.Application.JDBC_TEMPLATE;
import com.example.Data;
import com.example.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component ("createData")
public class CreateData implements Menu {
  public String getItemName() {
    return "Create new data ";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
      new Table(JDBC_TEMPLATE).create("Table.sql");
      System.out.println("Tables created successfully");
      System.out.println("Please wait...");
      new Data(new Random()).createAll(JDBC_TEMPLATE);
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
