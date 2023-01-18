package com.example.model;

import lombok.Data;

@Data
public class Group {

  private int id;
  private String name;
  private int number_student;

  public Group(String name) {
    this.name = name;
  }

  public Group() {
  }
}
