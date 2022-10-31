package com.example.dao;

import static com.example.spring_boot.Application.COURSE_DAO;
import static com.example.spring_boot.Application.GROUP_DAO;

import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<Student> mapper;

  @Autowired

  public StudentDao(JdbcTemplate jdbcTemplate,
      @Qualifier("mapperStud") BeanPropertyRowMapper<Student> mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  public void add(List<Student> studentList) {
    jdbcTemplate.batchUpdate("INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, studentList.get(i).getGroup_id());
            ps.setString(2, studentList.get(i).getFirst_name());
            ps.setString(3, studentList.get(i).getLast_name());
          }
          public int getBatchSize() {
            return studentList.size();
          }
        });
  }

  public void add(Student student) {
    jdbcTemplate.update("INSERT INTO students(group_id, first_name, last_name)VALUES(?,?,?);",
        student.getGroup_id(), student.getFirst_name(), student.getLast_name());
  }

  public List<Student> getStudent() {
    List<Course> courseListFull = COURSE_DAO.getAll();
    List<Student> studentList = jdbcTemplate.query("SELECT * FROM students;", mapper);
    List<Group> groupList = GROUP_DAO.getAll();
    List<Integer[]> studentsCoursesLinks = jdbcTemplate.query("SELECT * FROM students_courses ;",
        (rs, rowNum) -> {
          Integer[] studentsCourse = new Integer[2];
          studentsCourse[0] = rs.getInt(1);
          studentsCourse[1] = rs.getInt(2);
          return studentsCourse;
        });
    List<Student> studentListNew = new ArrayList<>();
    for (Student stud : studentList) {
      for (Group group : groupList) {
        if (stud.getGroup_id() == group.getGroup_id()) {
          stud.setGroup_name(group.getGroup_name());
        }
      }

      List<Course> courseListTemp = new ArrayList<>();
      for (Integer[] i : studentsCoursesLinks) {

        if (i[0].equals(stud.getStudent_id())) {
          courseListTemp.add(courseListFull.get(i[1] - 1));
        }
        stud.setCourseList(courseListTemp);
      }
      studentListNew.add(stud);
    }
    return studentListNew;
  }

  public List<Student> getStudentWithOutCourse(int courseId) {
    List<Student> studentList = new ArrayList<>();
    for (Student stud : getStudent()) {
      List<Integer> courseIdList = new ArrayList<>();
      for (Course course : stud.getCourseList()) {
        courseIdList.add(course.getCourse_id());
      }
      if (!courseIdList.contains(courseId)) {
        studentList.add(stud);
      }
    }
    return studentList;
  }

  public List<Student> getStudentWithCourse() {
    List<Student> studentList = new ArrayList<>();
    for (Student stud : getStudent()) {
      if (stud.getCourseList() != null) {
        studentList.add(stud);
      }
    }
    return studentList;
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM students  WHERE student_id = ? ;", id);
  }

  public void addStudentsCourse(List<Student> studentList) {
    List<Integer[]> studentsCoursesList = new ArrayList<>();
    for (Student stud : studentList) {
      if (stud.getCourseList() != null) {
        for (Course course : stud.getCourseList()) {
          studentsCoursesList.add(new Integer[]{stud.getStudent_id(), course.getCourse_id()});
        }
      }
    }
    String sql = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";
    jdbcTemplate.batchUpdate(sql,
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, studentsCoursesList.get(i)[0]);
            ps.setInt(2, studentsCoursesList.get(i)[1]);
          }
          public int getBatchSize() {
            return studentsCoursesList.size();
          }
        });
  }


  public void addStudentsCourse(int studentId, int courseId) {

    String sql = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";
    List<Student> studentList = getStudentWithOutCourse(courseId);
    for (Student stud : studentList) {
      if (stud.getStudent_id() == studentId) {
        jdbcTemplate.update(sql, studentId, courseId);
      }
    }
  }

  public void deleteStudentFromCourse(int studentId, int courseId) {
    jdbcTemplate.update("DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;",
        studentId, courseId);
  }
}