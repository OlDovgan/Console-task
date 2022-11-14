package com.example.spring_boot;


import com.example.SpringConfig;
import com.example.menu.Menu;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

  public static final AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(
          SpringConfig.class);

  public static void main(String[] args) throws SQLException {
    SpringApplication.run(SpringConfig.class, args);

    Menu firstMenu = context.getBean("firstMenu", Menu.class);
    Menu secondMenu = context.getBean("secondMenu", Menu.class);
    firstMenu.executeMenu();
    while (true) {
      secondMenu.executeMenu();
    }
  }
}
