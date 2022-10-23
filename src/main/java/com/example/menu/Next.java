package com.example.menu;

import org.springframework.stereotype.Component;

@Component ("next")
public class Next implements Menu {

  @Override
  public String getItemName() {
    return "Use Current Data";
  }

  @Override
  public void executeMenu() {

  }
}
