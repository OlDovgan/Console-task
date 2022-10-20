package com.example.extra;

import com.example.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUtils {

  public static boolean isExistInStudents(JDBC jdbc, String firstName, String lastName)
      throws SQLException {
    boolean result = false;
    try (ResultSet res = jdbc.getDbConnection().createStatement()
        .executeQuery(
            "SELECT EXISTS(SELECT * FROM students WHERE students.first_name= '" + firstName + "'"
                + " AND students.last_name= '" + lastName + "');")) {
      if (res.next()) {
        result = res.getBoolean(1);
      }
      return result;
    }
  }

  public static boolean isExistStudentWithCourse(JDBC jdbc, int student_id, int course_id)
      throws SQLException {
    boolean result = false;
    try (
        ResultSet res = jdbc.getDbConnection().createStatement()
            .executeQuery(
                "SELECT EXISTS(SELECT * FROM students_courses WHERE students_courses.student_id= '"
                    + student_id + "' AND students_courses.course_id= '" + course_id + "');")) {
      if (res.next()) {
        result = res.getBoolean(1);
      }
      return result;
    }
  }

  public static boolean isExistStudentId(JDBC jdbc, int student_id)
      throws SQLException {
    boolean result = false;
    try (
        ResultSet res = jdbc.getDbConnection().createStatement()
            .executeQuery(
                "SELECT EXISTS(SELECT * FROM students WHERE student_id= '" + student_id + "');")) {
      if (res.next()) {
        result = res.getBoolean(1);
      }
      return result;
    }
  }

  public static void clearData(JDBC jdbc) throws SQLException {

    try (Statement statement = jdbc.getDbConnection().createStatement()) {
      statement.executeUpdate(
          "TRUNCATE students, courses, groups, students_courses RESTART IDENTITY");
    }
  }
}
