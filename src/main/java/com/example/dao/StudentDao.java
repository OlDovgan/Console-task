package com.example.dao;

import com.example.model.Student;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {

  private final JdbcTemplate jdbcTemplate;
@Autowired
  public StudentDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void add(List<Student> studentList) {
    jdbcTemplate.batchUpdate("INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, studentList.get(i).getGroupId());
            ps.setString(2, studentList.get(i).getFirstName());
            ps.setString(3, studentList.get(i).getLastName());
          }

          public int getBatchSize() {
            return studentList.size();
          }
        });
  }

  public void add(Student student) {
    jdbcTemplate.update("INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);",
        student.getCourseId(), student.getFirstName(), student.getLastName());
  }

  public List<Integer> getId() {
    return jdbcTemplate.queryForList("SELECT student_id FROM students;", Integer.class);

  }

  public List<Student> getStudent() {
    //  return jdbcTemplate.query("SELECT * FROM students;", new StudentMapper());
    return jdbcTemplate.query("SELECT * FROM students;",
        new BeanPropertyRowMapper<>(Student.class));
  }

  public List<Student> getStudentWithCourse(String courseName) {
    String sql = "SELECT students.student_id,first_name, last_name,"
        + " courses.course_name, groups.group_name "
        + " FROM students JOIN groups "
        + " ON students.group_id= groups.group_id "
        + " JOIN students_courses "
        + " ON students.student_id=students_courses.student_id "
        + " JOIN courses "
        + " ON students_courses.course_id= courses.course_id "
        + " WHERE courses.course_name= ? ;";
    return jdbcTemplate.query(sql, rs -> {
      List<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student student = new Student();
        student.setStudentId(rs.getInt(1));
        student.setFirstName(rs.getString(2));
        student.setLastName(rs.getString(3));
        student.setCourseName(rs.getString(4));
        student.setGroupName(rs.getString(5));
        list.add(student);
      }
      return list;
    }, courseName);
  }

  public List<Student> getCoursesOfStudent(int studentId) {

    String sql = " SELECT students.student_id,students.first_name, students.last_name,"
        + " courses.course_id, courses.course_name "
        + " FROM students"
        + " JOIN students_courses"
        + " ON students.student_id=students_courses.student_id"
        + " JOIN courses"
        + " ON students_courses.course_id= courses.course_id"
        + " WHERE students.student_id = ? ;";
    return jdbcTemplate.query(sql, rs -> {
      List<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student student = new Student();
        student.setStudentId(rs.getInt(1));
        student.setFirstName(rs.getString(2));
        student.setLastName(rs.getString(3));
        student.setCourseId(rs.getInt(4));
        student.setCourseName(rs.getString(5));
        list.add(student);
      }
      return list;
    }, studentId);
  }

  public List<Student> getStudentsWithOutCourse(int course) {
    String sql = "SELECT student_id, first_name, last_name"
        + " FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id "
        + " AND students_courses.course_id = ? );";

    return jdbcTemplate.query(sql, rs -> {
      List<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student student = new Student();
        student.setStudentId(rs.getInt(1));
        student.setFirstName(rs.getString(2));
        student.setLastName(rs.getString(3));
        list.add(student);
      }
      return list;
    }, course);
  }

  public List<Student> getStudentsWhereCourseIsExists() {
    String sql = "SELECT student_id, first_name, last_name FROM students WHERE EXISTS"
        + " (SELECT course_id FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id  );";
    return jdbcTemplate.query(sql, rs -> {
      List<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student student = new Student();
        student.setStudentId(rs.getInt(1));
        student.setFirstName(rs.getString(2));
        student.setLastName(rs.getString(3));
        list.add(student);
      }
      return list;
    });
  }

  public boolean getStudentsWhereCourseIsNotExists(int studId, int courseId) {
    String sql = "SELECT EXISTS"
        + " (SELECT student_id FROM students WHERE student_id = ? AND NOT EXISTS"
        + " (SELECT * FROM students_courses WHERE students_courses.student_id=students.student_id"
        + " AND students_courses.course_id = ? ));";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, studId, courseId));
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM students  WHERE student_id = ? ;", id);
  }
}