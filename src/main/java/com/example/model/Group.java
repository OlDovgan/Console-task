package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
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
