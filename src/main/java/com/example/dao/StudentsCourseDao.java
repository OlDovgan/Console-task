package com.example.dao;

import com.example.model.StudentsCourse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentsCourseDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public StudentsCourseDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void add(List<StudentsCourse> studentsCourseList) {
    jdbcTemplate.batchUpdate("INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, studentsCourseList.get(i).getStudentId());
            ps.setInt(2, studentsCourseList.get(i).getCourseId());
          }
          public int getBatchSize() {
            return studentsCourseList.size();
          }
        });
  }

  public void delete(int studentId, int courseId) {
    jdbcTemplate.update("DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;",
        studentId, courseId);
  }

  public void add(int studentId, int courseId) {
    String sqlSelect = "SELECT EXISTS(SELECT * FROM students_courses WHERE "
        + "student_id= ? AND course_id= ? );";
    jdbcTemplate.query(sqlSelect, rs -> {
      if (!rs.getBoolean(1)) {
        jdbcTemplate.update(
            "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?);",
            studentId, courseId);
      }
    }, studentId, courseId);
  }
}
