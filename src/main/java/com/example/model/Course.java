package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class Course {

  private int courseId;
  private String courseName;
  private String courseDescription;

  @Autowired
  public Course(String courseName, String courseDescription) {
    this.courseName = courseName;
    this.courseDescription = courseDescription;
  }

  public Course() {
  }
}
