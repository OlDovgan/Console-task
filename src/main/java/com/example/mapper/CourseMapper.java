package com.example.mapper;

import com.example.model.Course;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements RowMapper<Course> {

  @Override
  public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
    Course course = new Course();
    course.setId(rs.getInt("course_id"));
    course.setName(rs.getString("course_name"));
    course.setDescription(rs.getString("course_description"));
    return course;
  }
}
