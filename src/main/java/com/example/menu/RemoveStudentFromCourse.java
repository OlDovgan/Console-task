package com.example.menu;

import static com.example.spring_boot.Application.STUDENT_DAO;

import com.example.Result;
import com.example.Settings;
import com.example.menu.MainMenu.SecondMenu;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(6)
public class RemoveStudentFromCourse implements Menu {
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";

  private final Settings setup;

  private final Result result;
@Autowired
  public RemoveStudentFromCourse(Result result, Settings setup) {
    this.result = result;
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
    System.out.println(result.studentsWhereCourseIsExists());
    int studentId = setup.readInt("Please, select a student ID to delete the course");
    System.out.println(result.studentsCourse(studentId));
    int courseId = setup.readInt("Please, select a course ID to delete" );
    STUDENT_DAO.deleteStudentFromCourse(studentId, courseId);
    System.out.println(result.studentsCourse(studentId));
    setup.endExecution();
  }
}
