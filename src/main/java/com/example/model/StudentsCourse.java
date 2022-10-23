package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourse {

  private int studentId;
  private int courseId;

  public StudentsCourse(int studentId, int courseId) {
    this.studentId = studentId;
    this.courseId = courseId;
  }
  public StudentsCourse() {}
}
