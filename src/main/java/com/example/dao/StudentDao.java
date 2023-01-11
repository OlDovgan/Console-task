package com.example.dao;


import com.example.model.Course;
import com.example.model.Student;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {

  @Autowired
  private GroupDao groupDao;
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  @Qualifier("mapperStudent")
  private RowMapper<Student> mapperStudent;
  @Autowired
  @Qualifier("mapperCourse")
  private RowMapper<Course> mapperCourse;

  public StudentDao(JdbcTemplate jdbcTemplate,
      RowMapper<Student> mapperStudent,
      CourseDao courseDao, GroupDao groupDao) {
    this.groupDao = groupDao;
    this.courseDao = courseDao;
    this.jdbcTemplate = jdbcTemplate;
    this.mapperStudent = mapperStudent;
  }

  public void add(List<Student> studentList) {

    for (Student student : studentList) {
      add(student);
    }
  }

  private String sqlInsertToStudents = "INSERT INTO students (group_id, first_name, last_name) VALUES(?,?,?);";

  private int keyId(Student student) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sqlInsertToStudents,
          new String[]{"student_id"});
      ps.setInt(1, student.getGroupId());
      ps.setString(2, student.getFirstName());
      ps.setString(3, student.getLastName());
      return ps;
    }, keyHolder);
    return (int) keyHolder.getKey();
  }
  String sqlInsertToStudCourses = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";
  public void add(Student student) {
    student.setStudentId(keyId(student));
    List<Integer[]> studentsCoursesList = new ArrayList<>();
    if (student.getCourseList() != null) {
      for (Course course : student.getCourseList()) {
        studentsCoursesList.add(new Integer[]{student.getStudentId(), course.getCourseId()});
      }
      jdbcTemplate.batchUpdate(sqlInsertToStudCourses,
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
  }

  public void addStudentsCourse(int studentId, int courseId) {

    List<Student> studentList = getWithOutCourse(courseId);
    for (Student stud : studentList) {
      if (stud.getStudentId() == studentId) {
        jdbcTemplate.update(sqlInsertToStudCourses, studentId, courseId);
      }
    }
  }

  public void addStudentsCourse(List<Student> studentsList) {
    List<Integer[]> arr = new ArrayList<>();
    for (Student student : studentsList) {
      if (student.getCourseList() != null) {
        for (Course course : student.getCourseList()) {
          arr.add(new Integer[]{student.getStudentId(), course.getCourseId()});
        }
      }
    }

    String sql = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";
    jdbcTemplate.batchUpdate(sql,
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {

            ps.setInt(1, arr.get(i)[0]);
            ps.setInt(2, arr.get(i)[1]);
          }

          public int getBatchSize() {
            return arr.size();
          }
        });

  }


  public List<Student> getAll() {

    List<Student> studentListFromTableStudents = jdbcTemplate.query("SELECT * FROM students;",
        mapperStudent);
    List<Student> studentListNew = new ArrayList<>();
    for (Student student : studentListFromTableStudents) {
      setStudentsGroup(student);
      student.setCourseList(getStudentsCourseByStudentId(student.getStudentId()));
      studentListNew.add(student);
    }
    return studentListNew;
  }

  private List<Student> getAll(List<Student> studentList) {

    List<Student> studentListNew = new ArrayList<>();
    for (Student student : studentList) {
      setStudentsGroup(student);
      student.setCourseList(getStudentsCourseByStudentId(student.getStudentId()));
      studentListNew.add(student);
    }
    return studentListNew;
  }

  private List<Course> getStudentsCourseByStudentId(int studentId) {
    String sql =
        " SELECT students_courses.course_id,courses.course_name, courses.course_description"
            + " FROM students_courses JOIN courses "
            + " ON students_courses.course_id= courses.course_id "
            + " WHERE students_courses.student_id= ?;";
    return jdbcTemplate.query(sql, mapperCourse, studentId);
  }

  private void setStudentsGroup(Student student) {
    if (student.getGroupId() != 0) {
      var groupName = jdbcTemplate.queryForObject(
          "SELECT group_name FROM groups WHERE group_id = ? ;",
          String.class, student.getGroupId());
      student.setGroupName(groupName);
    }
  }

  public List<Student> getWithOutCourse(int courseId) {
    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id "
        + " AND students_courses.course_id = ? );";
    studentList = jdbcTemplate.query(sql, mapperStudent, courseId);
    return getAll(studentList);
  }

  public List<Student> getWithCourse() {

    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE  EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id );";
    studentList = jdbcTemplate.query(sql, mapperStudent);
    return getAll(studentList);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM students  WHERE student_id = ? ;", id);
  }


  public void deleteFromCourse(int studentId, int courseId) {
    jdbcTemplate.update("DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;",
        studentId, courseId);
  }
}