package com.example.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Student {

  private int studentId;
  private int groupId;
  private String firstName;
  private String lastName;
  private List<Course> courseList= new ArrayList<>();
  private String groupName;

}
