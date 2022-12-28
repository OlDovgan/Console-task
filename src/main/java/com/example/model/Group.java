package com.example.model;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Data
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
  public Group(String groupName){
    this.groupName=groupName;
  }
}
