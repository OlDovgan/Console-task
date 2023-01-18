package com.example.mapper;

import com.example.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class StudentMapper implements RowMapper<Student> {

  @Override
   public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
   Student  student = new Student();
    student.setId(rs.getInt("student_id"));
    student.setGroupId(rs.getInt("group_id"));
    student.setFirstName(rs.getString("first_name"));
    student.setLastName(rs.getString("last_name"));
    return student;
  }
}
