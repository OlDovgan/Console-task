package com.example.menu;

<<<<<<< HEAD
import com.example.Data;
=======

import com.example.service.Data;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {

<<<<<<< HEAD
  private final Data data;

=======
  private Data data;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)

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
<<<<<<< HEAD
      data.addAllData();
=======
      //data.clearAll();
      data.createAll();
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
