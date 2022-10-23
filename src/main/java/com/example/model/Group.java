package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {

  private int groupId;
  private String groupName;
  private int numberStudent;

  public Group(String groupName, int numberStudent) {
    this.groupName = groupName;
    this.numberStudent = numberStudent;
  }
  public Group() {
  }
}
