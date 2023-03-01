package com.example.service;


import com.example.menu.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AutoRun implements ApplicationRunner {

  @Autowired
  Data data;
  @Autowired
  MainMenu firstMenu;
  @Autowired
  MainMenu secondMenu;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    data.createData();
    firstMenu.executeMenu();
    while (true) {
      secondMenu.executeMenu();
    }
  }
}
