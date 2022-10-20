package com.example.entity;

public class StudentsCourse {
  private int studentId;
  private int courseId;

  public StudentsCourse (int studentId, int courseId) {
    this.studentId=studentId;
    this.courseId= courseId;
  }
  public int getStudentId() {
    return studentId;
  }

  public int getCourseId() {
    return courseId;
  }
}
