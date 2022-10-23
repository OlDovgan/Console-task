package com.example.dao;

import com.example.model.StudentsCourse;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StudentCourseMapper implements RowMapper<StudentsCourse> {

  @Override
  public StudentsCourse mapRow(ResultSet result, int rowNum) throws SQLException {

    StudentsCourse studentsCourse = new StudentsCourse();
    studentsCourse.setStudentId(result.getInt(1));
    studentsCourse.setCourseId(result.getInt(2));
    return studentsCourse;
  }
}
