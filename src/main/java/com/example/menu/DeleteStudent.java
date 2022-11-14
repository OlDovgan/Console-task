package com.example.menu;

import com.example.Result;
import com.example.Settings;
import com.example.dao.StudentDao;
import com.example.menu.MainMenu.SecondMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(4)
public class DeleteStudent implements Menu {

  private final Settings settings;
  private final Result result;
  private final StudentDao studentDao;

  @Autowired
  public DeleteStudent(Settings settings, Result result, StudentDao studentDao) {
    this.result = result;
    this.settings = settings;
    this.studentDao = studentDao;
  }

  @Override
  public String getItemName() {
    return "Delete student by student ID";
  }

  @Override
  public void executeMenu() {
    System.out.println(result.studentInfoPrint());
    studentDao.delete(settings.readInt("Please make your choice"));
    System.out.println(result.studentInfoPrint());
    settings.endExecution();
  }
}
