package com.example.menu;


import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@FirstMenu
public class CreateData implements Menu {
  @Value("${courses}")
  private int coursesNumber;

  private final CourseService courseService;
  private final GroupService groupService;
  private final StudentService studentService;

  public String getItemName() {
    return "Create new data ";
  }

  @Autowired
  public CreateData(CourseService courseService, GroupService groupService,
      StudentService studentService) {
    this.courseService = courseService;
    this.groupService = groupService;
    this.studentService = studentService;
  }

  @Override
  public void executeMenu() {
    try {
      System.out.println("Please wait...");
      courseService.createNewData(coursesNumber);
      groupService.createData();
      studentService.createData();
      System.out.println("Data entered into database successfully" + System.lineSeparator());
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
