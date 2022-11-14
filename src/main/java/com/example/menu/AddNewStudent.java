package com.example.menu;

import com.example.Settings;
import com.example.dao.StudentDao;
import com.example.menu.MainMenu.SecondMenu;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(3)
public class AddNewStudent implements Menu {

  private final StudentDao studentDao;

  private final Settings settings;

  @Autowired
  public AddNewStudent(Settings settings, StudentDao studentDao) {
    this.studentDao = studentDao;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Add new student";
  }

  @Override
  public void executeMenu() {
    String[] nameStudent = settings.
        readString("Please enter name and surname of the student").split(" +");
    Student student = new Student();
    student.setFirstName(nameStudent[0]);
    student.setLastName(nameStudent[1]);
    studentDao.add(student);
    System.out.println(
        "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
    settings.endExecution();
  }
}
