package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import java.util.StringJoiner;

public class FindStudentsWithCourse implements Menu {

  private final Settings settings;
  private final Request request;

  public FindStudentsWithCourse(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Find all students related to course with given name";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println(request.coursesInfoPrint());
      int course = 0;
      while (course < 1 || course > request.courses.size()) {
        course = settings.readInt("Please select a course name from the list");
      }
      String formatString = "%-3s| %-" + 12 + "s| %-" + 12 + "s| %-" +
          (request.courses.get(course - 1).length() + 2) + "s| %-" + 10 + "s";
      StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
      stringJoiner.add(
          String.format(formatString, "ID", "First name", "Last name", "Course ",
              "Group name"));
      stringJoiner.add(
          String.format(formatString, " ", " ", " ", " ", " ")
              .replace(' ', '-')
              .replace('|', '+'));
      System.out.println(stringJoiner + System.lineSeparator() +
          request.findStudentsWithCourse(request.courses.get(course - 1)));
      settings.endExecution();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
