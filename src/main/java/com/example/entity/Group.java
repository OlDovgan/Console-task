package com.example.entity;

public class Group {


  private int groupId;
  private String groupName;
  private int numberStudent;


  public Group(String groupName) {
    this.groupName = groupName;
  }

  public Group() {
  }

  public int getNumberStudent() {
    return numberStudent;
  }

  public void setNumberStudent(int numberStudent) {
    this.numberStudent = numberStudent;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
