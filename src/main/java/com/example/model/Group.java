package com.example.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Group {

  private int groupId;
  private String groupName;
  private int numberStudent;

  @Autowired
  public Group(int numberStudent, String groupName) {
    this.groupName = groupName;
    this.numberStudent = numberStudent;
  }

  public Group() {
  }
}
