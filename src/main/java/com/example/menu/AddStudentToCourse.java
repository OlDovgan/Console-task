package com.example.menu;


import com.example.Result;
import com.example.Utility;
import com.example.model.Course;
import com.example.service.CourseService;
import com.example.service.StudentService;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(5)
public class AddStudentToCourse implements Menu {

  private final CourseService courseService;
  private final StudentService studentService;
  final String separator = System.lineSeparator();
  private final Utility service;
  private final Result result;

  @Autowired
  public AddStudentToCourse(StudentService studentService, Utility service, Result result,
      CourseService courseService) {
    this.studentService = studentService;
    this.courseService = courseService;
    this.result = result;
    this.service = service;
  }

  @Override
  public String getItemName() {
    return "Add a student to the course (from a list)";
  }

  @Override
  public void executeMenu() {
    List<Course> courseList = courseService.getAll();
    System.out.println(result.coursesInfo(courseList));
    int courseNumber = service.readInt(courseList.size());
    printTableHeader();
    printInfo(courseNumber);
    int studId = service.getStudentId(studentService.getWithOutCourse(courseNumber));
    studentService.addStudentsCourse(studId, courseNumber);
    System.out.println(result.getStudentsCourse(studId));
    service.endExecution();
  }

  private void printTableHeader() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    stringJoiner.add(String.format(format, "ID", "First name", "Last mame"));
    stringJoiner.add(String.format(format, "-", "-", "-").replace('|', '+')
        .replace(' ', '-'));
  }

  private void printInfo(int courseNumber) {
    System.out.println(result.studentsWithOutCourse(courseNumber) + separator);
    System.out.println(
        "You are going to add a student to course of " + result.getCourseNames()
            .get(courseNumber - 1));
  }
}

