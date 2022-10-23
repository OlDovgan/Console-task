package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component ("addStudent")
public class AddNewStudent implements Menu {

  private final Settings settings;
  private final Request request;

  @Autowired
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
    System.out.println("Please enter name and surname of the student");
    String[] nameStudent = new Scanner(System.in).nextLine().split(" ", 2);
    request.addStudent(nameStudent[0], nameStudent[1]);
    System.out.println(
        "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
    settings.endExecution();
  }
}
