package com.example.entity;

public class Student {

  private int studentId;
  private int groupId;
  private String firstName;
  private String lastName;
  private int courseId;
  private String courseName;
  private String groupName;

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public int getCourseId() {
    return courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getGroupName() {
    return groupName;
  }


  public int getStudentId() {
    return studentId;
  }

  public int getGroupId() {
    return groupId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

}
