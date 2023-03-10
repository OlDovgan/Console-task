package com.example.layer.service;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Data {

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

  public void createData() throws IOException, URISyntaxException {
    if (courseService.getAll().isEmpty()) {
      courseService.createData();
    }
    if (groupService.getAll().isEmpty()) {
      groupService.createData();
    }
    if (studentService.getAll().isEmpty()) {
      studentService.createData();
    }
  }
}
