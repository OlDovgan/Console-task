package com.example.menu;

import com.example.Utility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MainMenu implements Menu {


  private final List<Menu> childMenu = new ArrayList<>();
  private Utility utility;

  @Autowired
  public MainMenu(Utility utility) {
    this.utility = utility;
  }

  public MainMenu() {
  }

  public void addMenuItem(Menu menu) {
    childMenu.add(menu);
  }

  private void printMenu() {
    int i = 1;
    for (Menu s : childMenu) {
      System.out.println(i++ + ". " + s.getItemName());
    }
  }

  @Override
  public String getItemName() {
    return "Main menu";
  }

  @Override
  public void executeMenu() throws SQLException {
    printMenu();
     int menuItem = utility.readInt(childMenu.size());
    childMenu.get(menuItem - 1).executeMenu();
    System.out.println("Please make your choice");
  }
}

