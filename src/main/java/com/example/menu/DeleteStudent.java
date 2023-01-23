package com.example.menu;

import com.example.Result;
import com.example.Utility;
<<<<<<< HEAD
import com.example.dao.StudentDao;
=======
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(4)
public class DeleteStudent implements Menu {

  private final Utility utility;
  private final Result result;
<<<<<<< HEAD
  private final StudentDao studentDao;

  @Autowired
  public DeleteStudent(Utility utility, Result result, StudentDao studentDao) {
    this.result = result;
    this.utility = utility;
    this.studentDao = studentDao;
=======
  private final StudentService studentService;

  @Autowired
  public DeleteStudent(Utility utility, Result result, StudentService studentService) {
    this.result = result;
    this.utility = utility;
    this.studentService = studentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  }

  @Override
  public String getItemName() {
    return "Delete student by student ID";
  }

  @Override
  public void executeMenu() {
    System.out.println(result.studentInfoPrint());
<<<<<<< HEAD
    studentDao.delete(utility.readInt("Please make your choice"));
=======
    studentService.delete(utility.readInt("Please make your choice"));
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    System.out.println(result.studentInfoPrint());
    utility.endExecution();
  }
}
