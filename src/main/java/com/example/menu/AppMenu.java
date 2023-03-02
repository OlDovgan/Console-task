package com.example.menu;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppMenu {
  @Autowired
  private MainMenu firstMenu;
  @Autowired
  private MainMenu secondMenu;
  public void run() throws SQLException {
    firstMenu.executeMenu();
    while (true) {
      secondMenu.executeMenu();
    }
  }

}
