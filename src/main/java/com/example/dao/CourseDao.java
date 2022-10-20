package com.example.dao;

import com.example.JDBC;
import com.example.entity.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

  public void add(JDBC jdbc, List<Course> courseList) throws SQLException {
    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO courses (course_name,course_description) VALUES (?,?);")) {
      for (Course course : courseList) {
        preparedStatement.setString(1, course.getCourseName());
        preparedStatement.setString(2, course.getCourseDescription());
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    }
  }

  public List<Course> getCourse(JDBC jdbc) throws SQLException {
    List<Course> list = new ArrayList<>();
    try (Connection connection = jdbc.getDbConnection();
        ResultSet res = connection.createStatement()
            .executeQuery("SELECT * FROM courses ORDER BY course_id;")) {
      while (res.next()) {
        list.add(new Course(res.getInt(1), res.getString(2),
            res.getString(3)));
      }
      return list;
    }
  }
}
