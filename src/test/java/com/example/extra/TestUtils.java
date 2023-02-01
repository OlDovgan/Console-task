package com.example.extra;


import com.example.dao.GroupDao;
import com.example.mapper.CourseMapper;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import com.example.service.StudentService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@Service
public class TestUtils {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  CourseMapper mapperCourse;

  @Autowired
  GroupDao groupDao;
  @Autowired
  private StudentService studentService;

  public boolean isExistStudentId(int student_id) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE student_id= ? );";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, student_id));
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

  @DirtiesContext
  public List<Student> createStudentList() {
    List<Course> courses = new ArrayList<>();
    String description = " English is a language—originally the language of the people of England";
    String description2 = " Probability theory is the branch of mathematics concerned with probability";
    Course first = new Course("English", description);
    Course second = new Course("Probability theory", description2);
    first.setId(2);
    second.setId(4);
    courses.add(first);
    courses.add(second);
    Student student = new Student();
    student.setId(1);
    student.setGroupId(1);
    student.setFirstName("Amir");
    student.setLastName("Watson");
    student.setGroupName("nA-51");
    student.setCourse(courses);
    Student studentNext = new Student();
    studentNext.setId(2);
    studentNext.setGroupId(3);
    studentNext.setFirstName("Rex");
    studentNext.setLastName("Philip");
    studentNext.setGroupName("Jp-04");
    List<Student> studentListExpect = new ArrayList<>();
    studentListExpect.add(student);
    studentListExpect.add(studentNext);
    return studentListExpect;
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

  private Student createStudent(int groupId, String firstName, String lastName) {
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

  public void clearCourse() {
    jdbcTemplate.update("TRUNCATE  courses RESTART IDENTITY");
  }

  public void createStudentInDb() throws IOException, URISyntaxException {
    studentService.createData();
  }

  public void clearStudent() {
    jdbcTemplate.update("TRUNCATE students, students_courses RESTART IDENTITY");
  }
}
