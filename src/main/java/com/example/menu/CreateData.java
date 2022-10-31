package com.example.menu;

import static com.example.spring_boot.Application.DATA;
import static com.example.spring_boot.Application.JDBC_TEMPLATE;
import static com.example.spring_boot.Application.TABLE;

import com.example.Data;
import com.example.Table;
import com.example.menu.MainMenu.FirstMenu;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {

  public String getItemName() {
    return "Create new data ";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
     // new Table(JDBC_TEMPLATE).create("Table.sql");
      TABLE.create("Table.sql");
      System.out.println("Tables created successfully");
      System.out.println("Please wait...");
  //    new Data(new Random()).createAll();
      DATA.createAll();
   //   new Data(new Random()).createAll();
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
