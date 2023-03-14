package com.example.dao;


import com.example.mapper.CourseMapper;
import com.example.mapper.StudentMapper;
import com.example.model.Course;
import com.example.model.Student;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDao {

  private static final Logger logger
      = LoggerFactory.getLogger(StudentDao.class.getName());
  private final JdbcTemplate jdbcTemplate;
  private final StudentMapper mapperStudent;
  private final CourseMapper mapperCourse;
  private String query = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";

  @Autowired
  public StudentDao(JdbcTemplate jdbcTemplate, StudentMapper mapperStudent,
      CourseMapper mapperCourse) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapperStudent = mapperStudent;
    this.mapperCourse = mapperCourse;
  }

  public void add(List<Student> studentList) {
    logger.trace("Start add(List<Student> studentList)");

    for (Student student : studentList) {
      add(student);
    }
    logger.trace("Finish add(List<Student> studentList)");
  }

  private int keyId(Student student) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES(?,?,?);";
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql,
          new String[]{"student_id"});
      ps.setInt(1, student.getGroupId());
      ps.setString(2, student.getFirstName());
      ps.setString(3, student.getLastName());
      return ps;
    }, keyHolder);
    return (int) keyHolder.getKey();
  }


  public void add(Student student) {
    logger.trace("Start add(Student student)");
    student.setId(keyId(student));
    List<Integer[]> studentsCoursesList = new ArrayList<>();
    if (student.getCourse() != null) {
      for (Course course : student.getCourse()) {
        studentsCoursesList.add(new Integer[]{student.getId(), course.getId()});
      }
      jdbcTemplate.batchUpdate(query,
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
    logger.trace("Finish add(Student student)");
  }

  public void addStudentsCourse(int studentId, int courseId) {
    logger.trace("Start addStudentsCourse(int studentId, int courseId) ");
    jdbcTemplate.update(query, studentId, courseId);
    logger.trace("Finish addStudentsCourse(int studentId, int courseId) ");
  }

  public void addStudentsCourse(List<Student> studentsList) {
    logger.trace("Start addStudentsCourse(List<Student> studentsList) ");

    List<Integer[]> list = new ArrayList<>();
    for (Student student : studentsList) {
      if (student.getCourse() != null) {
        for (Course course : student.getCourse()) {
          list.add(new Integer[]{student.getId(), course.getId()});
        }
      }
    }

    jdbcTemplate.batchUpdate(query,
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {

            ps.setInt(1, list.get(i)[0]);
            ps.setInt(2, list.get(i)[1]);
          }

          public int getBatchSize() {
            return list.size();
          }
        });
    logger.trace("Finish addStudentsCourse(List<Student> studentsList) ");
  }

  public List<Student> getAll() {
    logger.trace("Start getAll() ");

    List<Student> studentListFromTableStudents = jdbcTemplate.query("SELECT * FROM students;",
        mapperStudent);
    List<Student> studentListNew = new ArrayList<>();
    for (Student student : studentListFromTableStudents) {
      setStudentsGroupsName(student);
      student.setCourse(getStudentsCourseByStudentId(student.getId()));
      studentListNew.add(student);
    }
    logger.trace("Finish getAll() ");
    return studentListNew;
  }

  public Student getStudentById(int id) {
    logger.trace("Start getStudentById(int id) ");

    String sql = "SELECT * FROM students WHERE student_id = ?";
    Student student = jdbcTemplate.queryForObject(sql, mapperStudent, id);
    if (student != null) {
      setStudentsGroupsName(student);
      student.setCourse(getStudentsCourseByStudentId(id));
    }
    logger.trace("Finish getStudentById(int id). Return student ");

    return student;
  }

  private List<Student> getAll(List<Student> studentList) {
    logger.trace("Start getAll(List<Student> studentList) ");

    List<Student> studentListNew = new ArrayList<>();
    for (Student student : studentList) {
      setStudentsGroupsName(student);
      student.setCourse(getStudentsCourseByStudentId(student.getId()));
      studentListNew.add(student);
    }
    logger.trace("Finish getAll(List<Student> studentList). Return  List<Student>  ");

    return studentListNew;
  }

  private List<Course> getStudentsCourseByStudentId(int studentId) {
    logger.trace("Start getStudentsCourseByStudentId(int studentId). Return  List<Course>");
    String sql =
        " SELECT students_courses.course_id,courses.course_name, courses.course_description"
            + " FROM students_courses JOIN courses "
            + " ON students_courses.course_id= courses.course_id "
            + " WHERE students_courses.student_id= ?;";

    return jdbcTemplate.query(sql, mapperCourse, studentId);

  }

  private void setStudentsGroupsName(Student student) {
    logger.trace("Start private void setStudentsGroupsName(Student student)");
    if (student.getGroupId() != 0) {
      var groupName = jdbcTemplate.queryForObject(
          "SELECT group_name FROM groups WHERE group_id = ? ;",
          String.class, student.getGroupId());
      student.setGroupName(groupName);
      logger.trace("student.setGroupName(" + groupName + ");");

    }
  }

  public List<Student> getWithOutCourse(int courseId) {
    logger.trace("Start getWithOutCourse(int courseId)");

    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id "
        + " AND students_courses.course_id = ? );";
    studentList = jdbcTemplate.query(sql, mapperStudent, courseId);
    logger.trace("Finish getWithOutCourse(int courseId). Return studentList");
    return getAll(studentList);
  }

  public List<Student> getWithCourse() {
    logger.trace("Start getWithCourse()");

    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE  EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id );";
    studentList = jdbcTemplate.query(sql, mapperStudent);
    logger.trace("Finish getWithCourse(). Return studentList");

    return getAll(studentList);
  }

  public List<Student> getWithCourse(String courseName) {
    logger.trace("Start getWithCourse(String courseName)");

    List<Student> studentList = new ArrayList<>();
    for (Student stud : getWithCourse()) {
      for (Course course : stud.getCourse()) {
        if (course.getName().equals(courseName)) {
          studentList.add(stud);
        }
      }
    }
    logger.trace("Finish getWithCourse(String courseName). Return studentList");

    return studentList;
  }

  public void delete(int id) {
    logger.trace("Start delete(int id)");
    jdbcTemplate.update("DELETE FROM students  WHERE student_id = ? ;", id);
    logger.trace("Finish delete(int id)");
  }

  public void deleteFromCourse(int studentId, int courseId) {
    logger.trace("Start deleteFromCourse(int studentId, int courseId)");
    jdbcTemplate.update("DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;",
        studentId, courseId);
    logger.trace("Finish deleteFromCourse(int studentId, int courseId)");

  }

  public void clearAll() {
    logger.trace("Start clearAll() - TRUNCATE students, students_courses RESTART IDENTITY;");
    jdbcTemplate.update("TRUNCATE students, students_courses RESTART IDENTITY;");
    logger.trace("Finish clearAll() - TRUNCATE students, students_courses RESTART IDENTITY;");
  }
}
