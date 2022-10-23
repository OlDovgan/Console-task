package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("findGroup")
public class FindAllGroups implements Menu {

  private final Settings settings;
  private final Request request;

  @Autowired
  public FindAllGroups(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Find all groups with less or equals student count";
  }

  @Override
  public void executeMenu() throws SQLException {
    int amount = settings.readInt("Please enter the number of students in the group ");
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    System.out.println(amount);
    stringJoiner.add("Num| Groups%n");
    stringJoiner.add("---+-------");
    stringJoiner.add(request.findGroups(amount));
    System.out.println(stringJoiner);
    settings.endExecution();
  }
}
