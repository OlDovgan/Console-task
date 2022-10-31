package com.example.dao;

import com.example.model.Course;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseDao {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<Course> mapper;

  @Autowired
  public CourseDao(JdbcTemplate jdbcTemplate,
      @Qualifier("mapperCourse") BeanPropertyRowMapper<Course> mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  public void add(Course course) {
    jdbcTemplate.update("INSERT INTO courses (course_name,course_description) VALUES (?,?);",
        course.getCourse_name(), course.getCourse_description());
  }

  public void add(List<Course> courseList) {
    jdbcTemplate.batchUpdate("INSERT INTO courses (course_name,course_description) VALUES (?,?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, courseList.get(i).getCourse_name());
            ps.setString(2, courseList.get(i).getCourse_description());
          }

          public int getBatchSize() {
            return courseList.size();
          }
        }
    );
  }

  public List<Course> getAll() {
    return jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id;",
        mapper);
  }
}
