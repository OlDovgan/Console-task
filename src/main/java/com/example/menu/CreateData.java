package com.example.menu;


import com.example.service.Data;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {

  private Data data;

  public String getItemName() {
    return "Create new data ";
  }

  @Autowired
  public CreateData(Data data) {
    this.data = data;
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
      //data.clearAll();
      data.createAll();
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
