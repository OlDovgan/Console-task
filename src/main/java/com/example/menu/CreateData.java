package com.example.menu;

import com.example.Data;
import com.example.Table;
import com.example.menu.MainMenu.FirstMenu;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {

  public String getItemName() {
    return "Create new data ";
  }

  private final Data data;
  private final Table table;

  @Autowired
  public CreateData(Data data, Table table) {

    this.data = data;
    this.table = table;
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
      table.create("Table.sql");
      System.out.println("Tables created successfully");
      System.out.println("Please wait...");
      data.createAll();
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
