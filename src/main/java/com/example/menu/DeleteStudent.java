package com.example.menu;

import com.example.Request;
import com.example.Settings;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("deleteStudent")
public class DeleteStudent implements Menu {

  private final Settings settings;
  private final Request request;
@Autowired
  public DeleteStudent(Settings settings, Request request) {
    this.request = request;
    this.settings = settings;
  }

  @Override
  public String getItemName() {
    return "Delete student by STUDENT_ID";
  }

  @Override
  public void executeMenu() {
    System.out.println(request.studentInfoPrint());
    request.deleteStudent(settings.readInt("Please make your choice"));
    System.out.println(request.studentInfoPrint());
    settings.endExecution();
  }
}
