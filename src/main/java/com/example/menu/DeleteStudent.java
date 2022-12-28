package com.example.menu;

import com.example.Result;
import com.example.Utility;
import com.example.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(4)
public class DeleteStudent implements Menu {

  private final Utility utility;
  private final Result result;
  private final StudentDao studentDao;

  @Autowired
  public DeleteStudent(Utility utility, Result result, StudentDao studentDao) {
    this.result = result;
    this.utility = utility;
    this.studentDao = studentDao;
  }

  @Override
  public String getItemName() {
    return "Delete student by student ID";
  }

  @Override
  public void executeMenu() {
    System.out.println(result.studentInfoPrint());
    studentDao.delete(utility.readInt("Please make your choice"));
    System.out.println(result.studentInfoPrint());
    utility.endExecution();
  }
}
