package com.example.menu;


import com.example.Result;
import com.example.Settings;
import com.example.dao.StudentDao;
import com.example.menu.MainMenu.SecondMenu;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(5)
public class AddStudentToCourse implements Menu {

  private final StudentDao studentDao;

  private final String separator = System.lineSeparator();
  private final Settings settings;
  private final Result result;

  @Autowired
  public AddStudentToCourse(StudentDao studentDao, Settings settings, Result result) {
    this.studentDao = studentDao;
    this.result = result;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Add a student to the course (from a list)";
  }

  @Override
  public void executeMenu() {
    System.out.println(result.coursesInfo());
    int course = 0;
    while (course < 1 || course > result.courses.size()) {
      course = settings.readInt(
          "Please, select the course you want to add the student to" + separator);
    }
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    stringJoiner.add(String.format(format, "ID", "First name", "Last mame"));
    stringJoiner.add(String.format(format, "-", "-", "-").replace('|', '+')
        .replace(' ', '-'));
    System.out.println(result.studentsWithOutCourse(course) + separator);
    System.out.println(
        "You are going to add a student to course of " + result.courses.get(course - 1));
    int studId = settings.readInt("Please, select a student ID to add to course of "
        + result.courses.get(course - 1));
    studentDao.addStudentsCourse(studId, course);
    System.out.println(result.studentsCourse(studId));
    settings.endExecution();
  }


}
