package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import java.util.StringJoiner;

public class RemoveStudentFromCourse implements Menu {
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";

  private final Settings setup = new Settings();
  private final Request request;

  public RemoveStudentFromCourse(Request request) {
    this.request = request;
  }

  @Override
  public String getItemName() {
    return "Remove the student from one of his or her courses";
  }

  @Override
  public void executeMenu() {
    StringJoiner title = new StringJoiner(System.lineSeparator());
    try {

      title.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
      title.add(String.format(FORMAT, "-", "-", " -")
          .replace('|', '+')
          .replace(' ', '-'));
      System.out.println(request.studentsWhereCourseIsExists());
      int studentId = setup.readInt("Please, select a student ID to delete the course");
      System.out.println(request.findCoursesOfStudent(studentId));
      int courseId = setup.readInt("Please, select a course ID to delete" );
      request.deleteStudentFromCourse(studentId, courseId);
      System.out.println(request.findCoursesOfStudent(studentId));
      setup.endExecution();
    } catch (SQLException e) {
    }
  }
}
