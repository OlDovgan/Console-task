package com.example.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int studentId;
  private int groupId;
  private String firstName;
  private String lastName;
  private List<Course> courseList;
  private String groupName;
}
