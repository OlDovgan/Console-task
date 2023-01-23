package com.example.menu;


import com.example.Result;
import com.example.Utility;
<<<<<<< HEAD
import com.example.dao.CourseDao;
import com.example.dao.StudentDao;
import com.example.model.Student;
=======
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(5)
public class AddStudentToCourse implements Menu {

<<<<<<< HEAD
  private final StudentDao studentDao;
  private final CourseDao courseDao;
=======
  private final CourseService courseService;
  private final StudentService studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  final String separator = System.lineSeparator();
  private final Utility service;
  private final Result result;

  @Autowired
<<<<<<< HEAD
  public AddStudentToCourse(StudentDao studentDao, Utility service, Result result,
      CourseDao courseDao) {
    this.studentDao = studentDao;
    this.courseDao = courseDao;
=======
  public AddStudentToCourse(StudentService studentService, Utility service, Result result,
      CourseService courseService) {
    this.studentService = studentService;
    this.courseService = courseService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    this.result = result;
    this.service = service;
  }

  @Override
  public String getItemName() {
    return "Add a student to the course (from a list)";
  }

  @Override
  public void executeMenu() {
    System.out.println(result.coursesInfo());
    int courseNumber = getCourseNumber();
    printTableHeader();
    printInfo(courseNumber);
    int studId = getStudentIdWithOutCourse(courseNumber);
<<<<<<< HEAD
    studentDao.addStudentsCourse(studId, courseNumber);
=======
    studentService.addStudentsCourse(studId, courseNumber);
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    System.out.println(result.getStudentsCourse(studId));
    service.endExecution();
  }

  private int getStudentIdWithOutCourse(int courseNumber) {
    int studId = 0;
    List<Integer> studIdList = new ArrayList<>();
<<<<<<< HEAD
    for (Student student : studentDao.getWithOutCourse(courseNumber)) {
=======
    for (Student student : studentService.getWithOutCourse(courseNumber)) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      studIdList.add(student.getId());
    }
    while (!studIdList.contains(studId)) {
      studId = service.readInt("Please, select a student ID to add to course of "
          + result.courses.get(courseNumber - 1));
    }
    return studId;
  }

  private int getCourseNumber() {
    int courseNumber = 0;

<<<<<<< HEAD
    while (courseNumber < 1 || courseNumber > courseDao.getAll().size()) {
=======
    while (courseNumber < 1 || courseNumber > courseService.getAll().size()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      courseNumber = service.readInt(
          "Please, select the course you want to add the student to" + separator);
    }
    return courseNumber;
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
        "You are going to add a student to course of " + result.courses.get(courseNumber - 1));
  }
}

