package com.example.dao;

import com.example.mapper.CourseMapper;
import com.example.model.Course;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseDao {
  private static final Logger logger
      = LoggerFactory.getLogger(CourseDao.class.getName());
  private final JdbcTemplate jdbcTemplate;
  private final CourseMapper mapper;

  @Autowired
  public CourseDao(JdbcTemplate jdbcTemplate,CourseMapper mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  public void add(Course course) {
    logger.trace("Start add(Course course)");
    jdbcTemplate.update("INSERT INTO courses (course_name,course_description) VALUES (?,?);",
        course.getName(), course.getDescription());
    logger.trace("Finish add(Course course)");

  }

  public void add(List<Course> courseList) {
    logger.trace("Start add(List<Course> courseList)");
    jdbcTemplate.batchUpdate("INSERT INTO courses (course_name,course_description) VALUES (?,?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, courseList.get(i).getName());
            ps.setString(2, courseList.get(i).getDescription());
          }

          public int getBatchSize() {
            return courseList.size();
          }
        }
    );
    logger.trace("Finish add(List<Course> courseList)");

  }
  public List<Course> getAll() {
    logger.trace("Start getAll() - SELECT * FROM courses ORDER BY course_id;");
    return jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id;", mapper);
  }
  public void clearAll() {
    logger.trace( "Start clearAll() - TRUNCATE  courses RESTART IDENTITY;");
    jdbcTemplate.update("TRUNCATE  courses RESTART IDENTITY;");
    logger.trace( "Finish clearAll() - TRUNCATE  courses RESTART IDENTITY;");

  }
}
