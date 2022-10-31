package com.example.extra;

import static com.example.ResultTest.jdbcTemplateTest;

import com.example.testcontainers.config.ContainersEnvironment;

public class TestUtils extends ContainersEnvironment {

  public static boolean isExistInStudents(String firstName, String lastName) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE students.first_name= ? "
        + " AND students.last_name= ?);";

    return Boolean.TRUE.equals(jdbcTemplateTest.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, firstName, lastName));
  }

  public static boolean isExistStudentWithCourse(int student_id, int course_id) {
    String sql =
        "SELECT EXISTS(SELECT * FROM students_courses WHERE students_courses.student_id= ? "
            + " AND students_courses.course_id= ? );";
    return Boolean.TRUE.equals(jdbcTemplateTest.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, student_id, course_id));
  }

  public static boolean isExistStudentId(int student_id) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE student_id= ? );";

    return Boolean.TRUE.equals(jdbcTemplateTest.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, student_id));
  }

  public static void clearData() {
    jdbcTemplateTest.update(
        "TRUNCATE students, courses, groups, students_courses RESTART IDENTITY");
  }
}
