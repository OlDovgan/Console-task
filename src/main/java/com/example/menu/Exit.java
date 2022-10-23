package com.example.menu;

import org.springframework.stereotype.Component;

@Component ("exit")
public class Exit implements Menu {

  public String getItemName() {
    return "Exit";
  }
  @Override
  public void executeMenu() {
    System.exit(0);
  }
}
