package com.example.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Group {

  private int id;
  private String name;
  private int numberStudent;

  @Autowired
  public Group(int numberStudent, String name) {
    this.name = name;
    this.numberStudent = numberStudent;
  }

  public Group() {
  }
}
