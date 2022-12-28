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
  private RowMapper<Student> mapper;
  @Autowired
  private RowMapper<Course> mapperCourse;

  public StudentDao(JdbcTemplate jdbcTemplate,
      RowMapper<Student> mapper,
      CourseDao courseDao, GroupDao groupDao) {
    this.groupDao = groupDao;
    this.courseDao = courseDao;
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }

  public void add(List<Student> studentList) {
    for (Student student : studentList) {
      add(student);
    }
  }

  public void add(Student student) {

    jdbcTemplate.update("INSERT INTO students(group_id, first_name, last_name) VALUES(?,?,?);",
        student.getGroupId(), student.getFirstName(), student.getLastName());
    int id = jdbcTemplate.queryForObject(
        "SELECT MAX (student_id) FROM students ;", Integer.class);
    student.setStudentId(id);
    List<Integer[]> studentsCoursesList = new ArrayList<>();
    if (student.getCourseList() != null) {
      for (Course course : student.getCourseList()) {
        studentsCoursesList.add(new Integer[]{student.getStudentId(), course.getCourseId()});
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
  }

  public void addStudentsCourse(int studentId, int courseId) {

    String sql = "INSERT INTO students_courses (student_id, course_id ) VALUES (?,?);";
    List<Student> studentList = getWithOutCourse(courseId);
    for (Student stud : studentList) {
      if (stud.getStudentId() == studentId) {
        jdbcTemplate.update(sql, studentId, courseId);
      }
    }
  }

  public List<Student> getAll() {

    List<Student> studentListFromTableStudents = jdbcTemplate.query("SELECT * FROM students;",
        mapper);
    List<Integer[]> studentsCoursesLinkFromDB = jdbcTemplate.query(
        "SELECT * FROM students_courses ;",
        (rs, rowNum) -> {
          Integer[] studentsCourse = new Integer[2];
          studentsCourse[0] = rs.getInt(1);
          studentsCourse[1] = rs.getInt(2);
          return studentsCourse;
        });

    List<Student> studentListNew = new ArrayList<>();
    List<Course> courseListFromDB = courseDao.getAll();
    for (Student student : studentListFromTableStudents) {
      setStudentsGroup(student);
      setNewCourseList(student, studentsCoursesLinkFromDB, courseListFromDB);
      studentListNew.add(student);
    }
    return studentListNew;
  }

  private List<Student> getAll(List<Student> studentList) {

    List<Integer[]> studentsCoursesLinkFromDB = jdbcTemplate.query(
        "SELECT * FROM students_courses ;",
        (rs, rowNum) -> {
          Integer[] studentsCourse = new Integer[2];
          studentsCourse[0] = rs.getInt(1);
          studentsCourse[1] = rs.getInt(2);
          return studentsCourse;
        });

    List<Student> studentListNew = new ArrayList<>();
    List<Course> courseListFromDB = courseDao.getAll();
    for (Student student : studentList) {
      setStudentsGroup(student);
      setNewCourseList(student, studentsCoursesLinkFromDB, courseListFromDB);
      studentListNew.add(student);
    }
    return studentListNew;
  }


  private void setStudentsGroup(Student student) {
    if (student.getGroupId() != 0) {
      var groupName = jdbcTemplate.queryForObject(
          "SELECT group_name FROM groups WHERE group_id = ? ;",
          String.class, student.getGroupId());
      student.setGroupName(groupName);
    }
  }

  private void setNewCourseList(Student student, List<Integer[]> studentsCourseLinkList,
      List<Course> courseListFull) {
    List<Course> courseListTemp = new ArrayList<>();
    for (Integer[] students_courses : studentsCourseLinkList) {
      if (students_courses[0].equals(student.getStudentId())) {
        for (Course course : courseListFull) {
          if (course.getCourseId() == students_courses[1]) {
            courseListTemp.add(course);
          }
        }
      }
      student.setCourseList(courseListTemp);
    }
  }


  public List<Student> getWithOutCourse(int courseId) {
    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id "
        + " AND students_courses.course_id = ? );";
    studentList = jdbcTemplate.query(sql, mapper, courseId);
    return getAll(studentList);
  }

  public List<Student> getWithCourse() {

    List<Student> studentList;
    String sql = "SELECT * FROM students WHERE  EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.student_id );";
    studentList = jdbcTemplate.query(sql, mapper);
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