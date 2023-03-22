package com.example.service;


import com.example.menu.AppMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AutoRun implements ApplicationRunner {


  @Autowired
  private Data data;
  @Autowired
  private AppMenu appMenu;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    data.createData();
    appMenu.run();
  }
}
