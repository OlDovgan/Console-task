package com.example.dao;

import com.example.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StudentMapper implements RowMapper<Student> {

  @Override
  public Student mapRow(ResultSet result, int rowNum) throws SQLException {
    Student student = new Student();
    student.setStudentId(result.getInt(1));
    student.setCourseId(result.getInt(2));
    student.setFirstName(result.getString(3));
    student.setLastName(result.getString(4));
    return student;
  }
}
