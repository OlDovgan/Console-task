package com.example.menu;

import com.example.Result;
import com.example.Utility;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(2)
public class FindStudentsWithCourse implements Menu {

  private final Utility service;
  private final Result result;

  @Autowired
  public FindStudentsWithCourse(Utility service, Result result) {
    this.result = result;
    this.service = service;
  }

  @Override
  public String getItemName() {
    return "Find all students related to course with given name";
  }

  @Override
  public void executeMenu() throws SQLException {
    System.out.println(result.coursesInfo());
    int courseNumber = 0;
    courseNumber = getCourseNumber();
    printTableHeader(courseNumber);
    System.out.println(result.studentsWithCourse(result.courses.get(courseNumber - 1)));
    service.endExecution();
  }

  private int getCourseNumber() {
    int courseNumber = 0;
    while (courseNumber < 1 || courseNumber > result.courses.size()) {
      courseNumber = service.readInt("Please select a course name from the list");
    }
    return courseNumber;
  }

  private void printTableHeader(int courseNumber) {
    String separator= "s| %-";
    String formatString = "%-3s| %-" + 12 + separator + 12 + separator +
        (result.courses.get(courseNumber - 1).length() + 2) + separator + 10 + "s";
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    stringJoiner.add(
        String.format(formatString, "ID", "First name", "Last name", "Course ",
            "Group name"));
    stringJoiner.add(
        String.format(formatString, " ", " ", " ", " ", " ")
            .replace(' ', '-')
            .replace('|', '+'));
    System.out.println(stringJoiner);
  }
}
