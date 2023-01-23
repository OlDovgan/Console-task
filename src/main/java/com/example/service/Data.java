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


  public void createAll()
      throws IOException, URISyntaxException {
    clearAll();
    courseService.createData();
    groupService.createData();
    studentService.createData();
  }

  public void clearAll() {
    courseService.clear();
    groupService.clear();
    studentService.clear();
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (courseService.getAll().size() < Integer.valueOf(studentsCoursesMax)) {
      courseService.createData();
    }
    if (groupService.getAll().isEmpty()) {
      groupService.clear();
      groupService.createData();
    }
    if (studentService.getAll().isEmpty()) {
      studentService.createData();
    }
  }
}
