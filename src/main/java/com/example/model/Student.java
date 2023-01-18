package com.example.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Student {

  private int id;
  private int groupId;
  private String firstName;
  private String lastName;
  private List<Course> course = new ArrayList<>();
  private String groupName;

}
