package com.example.menu;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(7)
public class Exit implements Menu {
  public String getItemName() {
    return "Exit";
  }
  @Override
  public void executeMenu() {
    System.exit(0);
  }
}
