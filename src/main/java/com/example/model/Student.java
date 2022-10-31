package com.example.model;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Data
@Component
public class Student {

  private int student_id;
  private int group_id;
  private String first_name;
  private String last_name;
  private List<Course> courseList;
  private String group_name;
}
