package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
  private int courseId;
  private  String courseName;
  private String courseDescription;

  public Course (String courseName, String courseDescription) {
    this.courseName = courseName;
    this.courseDescription = courseDescription;
  }
}
