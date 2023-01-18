package com.example.menu;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@FirstMenu
public class Next implements Menu {

  @Override
  public String getItemName() {
    return "Use Current Data";
  }

  @Override
  public void executeMenu() {

  }
}
