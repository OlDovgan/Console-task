package com.example.model;

import lombok.Data;

@Data
public class Course {

  private int id;
  private String name;
  private String description;

  public Course(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Course() {
  }
}
