package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
@Scope ("prototype")
public class Group {

  private int group_id;
  private String group_name;
  private int number_student;
  @Autowired
  public Group( int numberStudent, String groupName) {
    this.group_name = groupName;
    this.number_student = numberStudent;
  }
  public Group() {
  }
}
