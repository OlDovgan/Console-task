package com.example.dao;

import com.example.JDBC;
import com.example.entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

  public void add(JDBC jdbc, List<Student> studentList) throws SQLException {

    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);")) {
      for (Student s : studentList) {
        preparedStatement.setInt(1, s.getGroupId());
        preparedStatement.setString(2, s.getFirstName());
        preparedStatement.setString(3, s.getLastName());
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    }
  }

  public void add(JDBC jdbc, Student student) throws SQLException {

    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);")) {
      preparedStatement.setInt(1, student.getGroupId());
      preparedStatement.setString(2, student.getFirstName());
      preparedStatement.setString(3, student.getLastName());
      preparedStatement.executeUpdate();
    }
  }

  public List<Integer> getId(JDBC jdbc) throws SQLException {
    List<Integer> list = new ArrayList<>();
    try (Connection connection = jdbc.getDbConnection();
        ResultSet res = connection.createStatement()
            .executeQuery("SELECT student_id FROM students;")) {
      while (res.next()) {
        list.add(res.getInt(1));
      }
    }
    return list;
  }

  public List<Student> getStudent(JDBC jdbc) throws SQLException {
    List<Student> list = new ArrayList<>();
    try (Connection connection = jdbc.getDbConnection();
        ResultSet res = connection.createStatement()
            .executeQuery("SELECT * FROM students;")) {
      while (res.next()) {
        Student student = new Student();
        student.setStudentId(res.getInt(1));
        student.setCourseId(res.getInt(2));
        student.setFirstName(res.getString(3));
        student.setLastName(res.getString(4));
        list.add(student);
      }
      return list;
    }
  }
  public List<Student> getStudentWithCourse(JDBC jdbc, String courseName) throws SQLException {
    List<Student> list = new ArrayList<>();
    String sql = "SELECT students.student_id,first_name, last_name,"
        + " courses.course_name, groups.group_name "
        + " FROM students JOIN groups "
        + " ON students.group_id= groups.group_id "
        + " JOIN students_courses "
        + " ON students.student_id=students_courses.student_id "
        + " JOIN courses "
        + " ON students_courses.course_id= courses.course_id "
        + " WHERE courses.course_name= ?;";
    try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement(sql)) {
      preparedStatement.setString(1, courseName);
      ResultSet res = preparedStatement.executeQuery();
      while (res.next()) {
        Student student = new Student();
        student.setStudentId(res.getInt(1));
        student.setFirstName(res.getString(2));
        student.setLastName(res.getString(3));
        student.setCourseName(res.getString(4));
        student.setGroupName(res.getString(5));
        list.add(student);
      }
      return list;
    }
  }
  public List<Student> getCoursesOfStudent(JDBC jdbc, int studentId) throws SQLException {
    List<Student> list = new ArrayList<>();
    String sql = " SELECT students.student_id,students.first_name, students.last_name,"
        + " courses.course_id, courses.course_name "
        + " FROM students"
        + " JOIN students_courses"
        + " ON students.student_id=students_courses.student_id"
        + " JOIN courses"
        + " ON students_courses.course_id= courses.course_id"
        + " WHERE students.student_id = ? ;";
    try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement(sql)) {
      preparedStatement.setInt(1, studentId);
      ResultSet res = preparedStatement.executeQuery();
      while (res.next()) {
        Student student = new Student();
        student.setStudentId(res.getInt(1));
        student.setFirstName(res.getString(2));
        student.setLastName(res.getString(3));
        student.setCourseId(res.getInt(4));
        student.setCourseName(res.getString(5));
        list.add(student);
      }
      return list;
    }
  }
  public List<Student> getStudentsWithOutCourse(JDBC jdbc, int course) throws SQLException {
    List<Student> list = new ArrayList<>();
    try (PreparedStatement preparedStatement = jdbc.getDbConnection()
        .prepareStatement("SELECT student_id, first_name, last_name"
            + " FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
            + " WHERE students_courses.student_id=students.student_id "
            + " AND students_courses.course_id = ? );")) {
      preparedStatement.setInt(1, course);
      ResultSet res = preparedStatement.executeQuery();
      while (res.next()) {
        Student student = new Student();
        student.setStudentId(res.getInt(1));
        student.setFirstName(res.getString(2));
        student.setLastName(res.getString(3));
        list.add(student);
      }
      return list;
    }
  }

  public List<Student> getStudentsWhereCourseIsExists(JDBC jdbc) throws SQLException {
    List<Student> list = new ArrayList<>();
    try (Statement statement = jdbc.getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(
            "SELECT student_id, first_name, last_name FROM students WHERE EXISTS"
                + " (SELECT course_id FROM students_courses "
                + " WHERE students_courses.student_id=students.student_id  );")) {
      while (res.next()) {
        Student student = new Student();
        student.setStudentId(res.getInt(1));
        student.setFirstName(res.getString(2));
        student.setLastName(res.getString(3));
        list.add(student);
      }
      return list;
    }
  }
  public boolean getStudentsWhereCourseIsNotExists(JDBC jdbc,int studId, int courseId)
      throws SQLException {
    try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement("SELECT EXISTS"
            + " (SELECT student_id FROM students WHERE student_id = ? AND NOT EXISTS"
            + " (SELECT * FROM students_courses WHERE students_courses.student_id=students.student_id"
            + " AND students_courses.course_id = ? ));")) {
      preparedStatement.setInt(1,studId);
      preparedStatement.setInt(2,courseId);
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        return res.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }
  }
  public void delete(JDBC jdbc, int id)
      throws SQLException {
    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "DELETE FROM students  WHERE student_id = ? ;")) {
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
    }
  }
}