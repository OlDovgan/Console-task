package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import java.util.StringJoiner;

public class AddStudentToCourse implements Menu {

  private final String separator = System.lineSeparator();
  private final Settings settings;
  private final Request request;

  public AddStudentToCourse(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Add a student to the course (from a list)";
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println(request.coursesInfoPrint());
      int course = 0;
      while (course < 1 || course > request.courses.size()) {
        course = settings.readInt(
            "Please, select the course you want to add the student to" + separator);
      }
      StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
      String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
      stringJoiner.add(String.format(format, "ID", "First name", "Last mame"));
      stringJoiner.add(String.format(format, "-", "-", "-").replace('|', '+')
          .replace(' ', '-'));
      System.out.println(request.findStudentsWithOutCourse(course) + separator);
      System.out.println(
          "You are going to add a student to course of " + request.courses.get(course - 1));
      int stuId = settings.readInt("Please, select a student ID to add to course of "
          + request.courses.get(course - 1));
      while (!request.studentsWhereCourseIsNotExists(course, stuId)) {
        stuId = settings.readInt(
            "Please, select a student ID to add to course of" + request.courses.get(
                course - 1));
      }
      request.addStudentToCourse(stuId, course);
      System.out.println(request.findCoursesOfStudent(stuId));
      settings.endExecution();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
