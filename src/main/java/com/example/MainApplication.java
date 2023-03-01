package com.example;


import com.example.menu.Menu;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class
MainApplication {


  public static void main(String[] args) throws IOException {
    SpringApplication.run(MainApplication.class, args);

System.out.println(System.getProperty("java.util.logging.config.file"));
    //   Menu firstMenu = context.getBean("firstMenu", Menu.class);
    //   Menu secondMenu = context.getBean("secondMenu", Menu.class);
//    firstMenu.executeMenu();
//    while (true) {
//      secondMenu.executeMenu();
//    }
  }
}
