package com.example.menu;

import com.example.Utility;
import com.example.model.Student;
import com.example.layer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(3)
public class AddNewStudent implements Menu {


  private final StudentService studentService;

  private final Utility utility;

  @Autowired
  public AddNewStudent(Utility utility, StudentService studentService) {
    this.studentService = studentService;
    this.utility = utility;
  }

  @Override
  public String getItemName() {
    return "Add new student";
  }

  @Override
  public void executeMenu() {
    String[] nameStudent = utility.
        readString("Please enter name and surname of the student").split(" +");
    Student student = new Student();
    student.setFirstName(nameStudent[0]);
    student.setLastName(nameStudent[1]);
    studentService.add(student);
    System.out.println(
        "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
    utility.endExecution();
  }
}
