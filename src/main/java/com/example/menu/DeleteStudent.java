package com.example.menu;

import com.example.Result;
import com.example.Utility;
import com.example.model.Student;
import com.example.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(4)
public class DeleteStudent implements Menu {

  private final Utility utility;
  private final Result result;
  private final StudentService studentService;

  @Autowired
  public DeleteStudent(Utility utility, Result result, StudentService studentService) {
    this.result = result;
    this.utility = utility;
    this.studentService = studentService;
  }

  @Override
  public String getItemName() {
    return "Delete student by student ID";
  }

  @Override
  public void executeMenu() {
    List<Student> studentBeFor = studentService.getAll();
    System.out.println(result.studentInfoPrint(studentBeFor));
    studentService.delete(utility.getStudentId(studentBeFor));
    System.out.println(result.studentInfoPrint(studentService.getAll()));
    utility.endExecution();
  }
}
