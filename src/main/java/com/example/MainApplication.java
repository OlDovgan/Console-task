package com.example;


import com.example.menu.Menu;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {

  public static void main(String[] args) throws SQLException {
    ApplicationContext context = SpringApplication.run(MainApplication.class, args);
    Menu firstMenu = context.getBean("firstMenu", Menu.class);
    Menu secondMenu = context.getBean("secondMenu", Menu.class);
    firstMenu.executeMenu();
    while (true) {
      secondMenu.executeMenu();
    }
  }
}
