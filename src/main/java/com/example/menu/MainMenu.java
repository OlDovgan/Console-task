package com.example.menu;

import com.example.Settings;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class MainMenu implements Menu {

  private final List<Menu> childMenu = new ArrayList<>();
  private final Settings settings;

  @Autowired
  public MainMenu(Settings settings) {
    this.settings = settings;
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
    int menuItem = settings.readInt(childMenu.size());
    childMenu.get(menuItem - 1).executeMenu();
    System.out.println("Please make your choice");
  }
}

