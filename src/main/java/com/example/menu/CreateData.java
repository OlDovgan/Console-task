package com.example.menu;

import com.example.Data;
import com.example.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {

  private final Data data;
  private final Table table;

  public String getItemName() {
    return "Create new data ";
  }

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
      data.addAllData();
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
