package com.example.model;

import lombok.Data;

@Data
public class Course {

  private int courseId;
  private String courseName;
  private String courseDescription;

  public Course(String courseName, String courseDescription) {
    this.courseName = courseName;
    this.courseDescription = courseDescription;
  }

  public Course() {
  }
}
