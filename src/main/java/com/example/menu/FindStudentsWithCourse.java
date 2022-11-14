package com.example.menu;

import com.example.Result;
import com.example.Settings;
import com.example.menu.MainMenu.SecondMenu;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(2)
public class FindStudentsWithCourse implements Menu {

  private final Settings settings;
  private final Result result;

  @Autowired
  public FindStudentsWithCourse(Settings settings, Result result) {
    this.result = result;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Find all students related to course with given name";
  }

  @Override
  public void executeMenu() throws SQLException {
    System.out.println(result.coursesInfo());
    int course = 0;
    while (course < 1 || course > result.courses.size()) {
      course = settings.readInt("Please select a course name from the list");
    }
    String formatString = "%-3s| %-" + 12 + "s| %-" + 12 + "s| %-" +
        (result.courses.get(course - 1).length() + 2) + "s| %-" + 10 + "s";
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    stringJoiner.add(
        String.format(formatString, "ID", "First name", "Last name", "Course ",
            "Group name"));
    stringJoiner.add(
        String.format(formatString, " ", " ", " ", " ", " ")
            .replace(' ', '-')
            .replace('|', '+'));
    System.out.println(stringJoiner + System.lineSeparator() +
        result.studentsWithCourse(result.courses.get(course - 1)));
    settings.endExecution();
  }
}
