package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int studentId;
  private int groupId;
  private String firstName;
  private String lastName;

  private int courseId;
  private String courseName;
  private String groupName;
}
