package com.example.dao;

import com.example.JDBC;
import com.example.entity.StudentsCourse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StudentsCourseDao {
  public void add(JDBC jdbc, List<StudentsCourse> studentsCourse) throws SQLException {

    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);")) {
      for (StudentsCourse ss:studentsCourse      ) {
        preparedStatement.setInt(1, ss.getStudentId());
        preparedStatement.setInt(2, ss.getCourseId());
        preparedStatement.addBatch();
      }
     preparedStatement.executeBatch();
    }
  }
  public void delete(JDBC jdbc,int studentId, int courseId)
      throws SQLException {
    try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement(
        "DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;")) {
      preparedStatement.setInt(1, studentId);
      preparedStatement.setInt(2, courseId);
      preparedStatement.executeUpdate();
    }
  }

  public void add(JDBC jdbc,int student, int course) throws SQLException {
    try (Statement statement = jdbc.getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT EXISTS(SELECT * FROM students_courses WHERE "
            + "student_id=" + student + " AND course_id=" + course + ");")) {
      while (res.next()) {
        try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement(
            "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?);")) {
          preparedStatement.setInt(1, student);
          preparedStatement.setInt(2, course);
          preparedStatement.executeUpdate();
        }
      }
    }
  }
}
