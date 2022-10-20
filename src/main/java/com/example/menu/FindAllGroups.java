package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;


public class FindAllGroups implements Menu {

  private final Settings settings;
  private final Request request;

  public FindAllGroups(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Find all groups with less or equals student count";
  }

  @Override
  public void executeMenu() {
    try {
      String sep = System.lineSeparator();
      int amount = settings.readInt("Please enter the number of students in the group ");
      System.out.println(String.format("%s%s%s%s%s", "Num| Groups", sep, "---+-------",
          sep, request.findGroups(amount)));
      settings.endExecution();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
