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
public class Course {
  private int course_id;
  private  String course_name;
  private String course_description;
  @Autowired
  public Course (String courseName, String courseDescription) {
    this.course_name = courseName;
    this.course_description = courseDescription;
  }
  public Course (){};
}
