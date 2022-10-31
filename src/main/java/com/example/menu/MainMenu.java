package com.example.menu;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.Settings;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class MainMenu implements Menu {


  private final List<Menu> childMenu = new ArrayList<>();
  private Settings settings;

  @Qualifier
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
  @Retention(RUNTIME)
  public @interface FirstMenu {

  }

  @Qualifier
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
  @Retention(RUNTIME)
  public @interface SecondMenu {

  }

  @Autowired
  public MainMenu(Settings settings) {
    this.settings = settings;
  }

  private MainMenu() {
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

