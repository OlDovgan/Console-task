package com.example.menu;

import static com.example.spring_boot.Application.STUDENT_DAO;

import com.example.Settings;
import com.example.menu.MainMenu.SecondMenu;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@SecondMenu
@Order(3)
public class AddNewStudent implements Menu {

  private final Settings settings;

@Autowired
  public AddNewStudent(Settings settings) {
    this.settings = settings;
  }
  @Override
  public String getItemName() {
    return "Add new student";
  }

  @Override
  public void executeMenu() {

    System.out.println("Please enter name and surname of the student");
    String[] nameStudent = settings.readString("").split(" ", 2);
    Student student = new Student();
    student.setFirst_name(nameStudent[0]);
    student.setLast_name(nameStudent[1]);
    STUDENT_DAO.add(student);
    System.out.println(
        "Student " + nameStudent[0] + " " + nameStudent[1] + " has been successfully added");
    settings.endExecution();
  }
}
