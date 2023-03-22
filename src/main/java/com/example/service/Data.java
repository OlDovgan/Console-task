package com.example.service;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Data {

  private final Logger logger
      = LoggerFactory.getLogger(Data.class);

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
      logger.info("Courses table is empty");
      courseService.createData();
    }
    if (groupService.getAll().isEmpty()) {
      logger.info("Groups table is empty");
      groupService.createData();
    }
    if (studentService.getAll().isEmpty()) {
      logger.info("Students table is empty");
      studentService.createData();
    }
  }
}
