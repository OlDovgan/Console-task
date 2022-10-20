package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewStudent implements Menu {
  private final Settings settings;
  private final Request request;

  public AddNewStudent(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Add new student";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please enter name and surname of the student");
      String[] nameStudent = new Scanner(System.in).nextLine().split(" ", 2);
      request.addStudent(nameStudent[0], nameStudent[1]);
      System.out.println(
          "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
      settings.endExecution();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
