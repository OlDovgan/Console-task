package com.example.extra;


import com.example.dao.GroupDao;
import com.example.mapper.CourseMapper;
import com.example.mapper.GroupMapper;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  CourseMapper mapperCourse;

  @Autowired
  GroupDao groupDao;

  public boolean isExistStudentId(int student_id) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE student_id= ? );";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, student_id));
  }

  public Student findStudentById(int id) {
    return jdbcTemplate.queryForObject("SELECT * FROM students WHERE student_id= ? ;",
        new Object[]{id}, Student.class);
  }

  public List<Course> getCourseList() {
    return jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id;", mapperCourse);
  }

  public List<Group> getGroupList() {
    return groupDao.getAll();
  }

  public boolean isExistCourse(String course, String description) {
    String sql = "SELECT EXISTS(SELECT * FROM courses WHERE courses.course_name= ? "
        + " AND courses.course_description= ?);";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, course, description));
  }

  public boolean isExistGroup(String groupName) {
    String sql = "SELECT EXISTS(SELECT * FROM groups WHERE groups.group_name= ? );";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, groupName));
  }

  public boolean isExistStudentsCourse(int studentId, int courseId) {
    String sql = "SELECT EXISTS(SELECT * FROM students_courses WHERE student_id= ? AND course_id = ? );";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, studentId, courseId));
  }

  public List<Student> createStudentList(List<Course> courseList) {
    List<Student> studentList = new ArrayList<>();
    studentList.add(createStudent(3, "John", "Smith", courseList));
    studentList.add(createStudent(2, "Linda", "Lopes", courseList));
    studentList.add(createStudent(1, "Max", "Smith"));
    return studentList;
  }

  public Student createStudent(int groupId, String firstName, String lastName,
      List<Course> courseList) {
    Student student = new Student();
    student.setGroupId(groupId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setCourse(courseList);
    String str = jdbcTemplate.queryForObject("SELECT group_name FROM groups WHERE group_id = ? ",
        new Object[]{student.getGroupId()}, String.class);
    student.setGroupName(str);
    return student;
  }

  public Student createStudent(int groupId, String firstName, String lastName) {
    Student student = new Student();
    student.setGroupId(groupId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    String str = jdbcTemplate.queryForObject("SELECT group_name FROM groups WHERE group_id = ? ",
        new Object[]{student.getGroupId()}, String.class);
    student.setGroupName(str);
    return student;
  }


  public void clearData() {
    jdbcTemplate.update("TRUNCATE students, courses, groups, students_courses RESTART IDENTITY");
  }

  public void clearStudent() {
    jdbcTemplate.update("TRUNCATE students, students_courses RESTART IDENTITY");
  }
}
