package com.example.menu;

import com.example.Result;
import com.example.Utility;
import com.example.model.Student;
import com.example.service.StudentService;
import java.util.ArrayList;
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
    System.out.println(result.studentInfoPrint());
    studentService.delete(getStudentId());
    System.out.println(result.studentInfoPrint());
    utility.endExecution();
  }
  private int getStudentId() {
    int studId = 0;
    List<Integer> studIdList = new ArrayList<>();
    for (Student student : studentService.getAll()) {
      studIdList.add(student.getId());

    }
    while (!studIdList.contains(studId)) {
      studId = utility.readInt("Please, select a student ID to delete the course ");
    }
    return studId;
  }
}
