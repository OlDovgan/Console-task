package com.example.menu;

import com.example.Result;
import com.example.Utility;
<<<<<<< HEAD
import com.example.dao.StudentDao;
=======
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(6)
public class RemoveStudentFromCourse implements Menu {

<<<<<<< HEAD
  private final StudentDao studentDao;
=======
  private final StudentService studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  private final Utility service;
  private final Result result;

  @Autowired
<<<<<<< HEAD
  public RemoveStudentFromCourse(StudentDao studentDao, Result result, Utility service) {
    this.studentDao = studentDao;
=======
  public RemoveStudentFromCourse(StudentService studentService, Result result, Utility service) {
    this.studentService = studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    this.result = result;
    this.service = service;
  }

  @Override
  public String getItemName() {
    return "Remove the student from one of his or her courses";
  }

  @Override
  public void executeMenu() {
    printTableHeader();
    System.out.println(result.getStudentsWhereCourseIsExists());
    int studentId = service.readInt("Please, select a student ID to delete the course");
    System.out.println(result.getStudentsCourse(studentId));
    int courseId = service.readInt("Please, select a course ID to delete");
<<<<<<< HEAD
    studentDao.deleteFromCourse(studentId, courseId);
=======
    studentService.deleteFromCourse(studentId, courseId);
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    System.out.println(result.getStudentsCourse(studentId));
    service.endExecution();
  }

  private void printTableHeader() {
    StringJoiner title = new StringJoiner(System.lineSeparator());
    title.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
    title.add(String.format(FORMAT, "-", "-", " -")
        .replace('|', '+')
        .replace(' ', '-'));
    System.out.println(title);
  }
}
