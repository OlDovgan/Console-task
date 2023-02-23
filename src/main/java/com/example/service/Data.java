package com.example.service;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Data implements ApplicationRunner {

  @Value("${students-courses-max}")
  private String studentsCoursesMax;
  @Value("${courses}")
  private int coursesNumber;
  private final CourseService courseService;
  private final GroupService groupService;
  private final StudentService studentService;

  @Autowired
  public Data(CourseService courseService, GroupService groupService,
      StudentService studentService) {
    this.courseService = courseService;
    this.groupService = groupService;
    this.studentService = studentService;
  }


  @Override
  public void run(ApplicationArguments args) throws IOException, URISyntaxException {
    createData();

  }

  private void createData() throws IOException, URISyntaxException {
    if (courseService.getAll().size() < Integer.valueOf(studentsCoursesMax)) {
      courseService.createNewData(coursesNumber);
    }
    if (groupService.getAll().isEmpty()) {
      groupService.createData();
    }
    if (studentService.getAll().isEmpty()) {
      studentService.createData();
    }
  }
}
