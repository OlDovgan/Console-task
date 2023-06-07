package com.example.menu;

import com.example.Result;
import com.example.Utility;
import com.example.model.Student;
import com.example.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(6)
public class RemoveStudentFromCourse implements Menu {

  private final StudentService studentService;
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  private final Utility service;
  private final Result result;

  @Autowired
  public RemoveStudentFromCourse(StudentService studentService, Result result, Utility service) {
    this.studentService = studentService;
    this.result = result;
    this.service = service;
  }

  @Override
  public String getItemName() {
    return "Remove the student from one of his or her courses";
  }

  @Override
  public void executeMenu() throws Exception {
    printTableHeader();
    System.out.println(result.getStudentsWhereCourseIsExists());
    int studentId = getStudentIdWithCourse();
    System.out.println("The action completed successfully");
    System.out.println(result.getStudentsCourse(studentId));
    int courseId = service.readInt("Please, select a course ID to delete");
    studentService.deleteFromCourse(studentId, courseId);
    System.out.println(result.getStudentsCourse(studentId));
    service.endExecution();
  }
  private int getStudentIdWithCourse() {
    int studId = 0;
    List<Integer> studIdList = new ArrayList<>();
    for (Student student : studentService.getWithCourse()) {
      studIdList.add(student.getId());

    }
    while (!studIdList.contains(studId)) {
      studId = service.readInt("Please, select a student ID to delete the course ");
    }
    return studId;
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
