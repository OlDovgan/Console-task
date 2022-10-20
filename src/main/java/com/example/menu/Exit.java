package com.example.menu;

public class Exit implements Menu {

  public String getItemName() {
    return "Exit";
  }

  @Override
  public void executeMenu() {
    System.exit(0);
  }
}
