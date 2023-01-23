package com.example.menu;

import com.example.Utility;
<<<<<<< HEAD
import com.example.dao.StudentDao;
import com.example.model.Student;
=======
import com.example.model.Student;
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(3)
public class AddNewStudent implements Menu {

<<<<<<< HEAD
  private final StudentDao studentDao;
=======

  private final StudentService studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)

  private final Utility utility;

  @Autowired
<<<<<<< HEAD
  public AddNewStudent(Utility utility, StudentDao studentDao) {
    this.studentDao = studentDao;
=======
  public AddNewStudent(Utility utility, StudentService studentService) {
    this.studentService = studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
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
<<<<<<< HEAD
    studentDao.add(student);
=======
    studentService.add(student);
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    System.out.println(
        "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
    utility.endExecution();
  }
}
