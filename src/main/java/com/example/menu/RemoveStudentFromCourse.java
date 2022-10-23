package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component ("removeStudentFromCourse")
public class RemoveStudentFromCourse implements Menu {
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";

  private final Settings setup;

  private final Request request;
@Autowired
  public RemoveStudentFromCourse(Request request, Settings setup) {
    this.request = request;
    this.setup=setup;
  }

  @Override
  public String getItemName() {
    return "Remove the student from one of his or her courses";
  }

  @Override
  public void executeMenu() {
    StringJoiner title = new StringJoiner(System.lineSeparator());

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
  }
}
