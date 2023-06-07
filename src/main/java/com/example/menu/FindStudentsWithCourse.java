package com.example.menu;

import com.example.Result;
import com.example.Utility;
import com.example.service.CourseService;
import java.sql.SQLException;
import java.util.List;
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
  private CourseService courseService;

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
    System.out.println(result.coursesInfo(courseService.getAll()));
    int courseNumber = 0;
    List<String> courseNames = result.getCourseNames();
    courseNumber = service.readInt(courseNames.size());
    printTableHeader(courseNumber);
    System.out.println(result.studentsWithCourse(courseNames.get(courseNumber - 1)));
    service.endExecution();
  }

  private void printTableHeader(int courseNumber) {
    String separator = "s| %-";
    String formatString = "%-3s| %-" + 12 + separator + 12 + separator +
        (result.getCourseNames().get(courseNumber - 1).length() + 2) + separator + 10 + "s";
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
