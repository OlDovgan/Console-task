package com.example.dao;

import com.example.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StudentFullMapper implements RowMapper<Student> {

  @Override
  public Student mapRow(ResultSet result, int rowNum) throws SQLException {
    Student student = new Student();
    student.setStudentId(result.getInt(1));
    student.setFirstName(result.getString(2));
    student.setLastName(result.getString(3));
    student.setCourseName(result.getString(4));
    student.setGroupName(result.getString(5));
    return student;
  }
}
