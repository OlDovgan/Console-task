package com.example.extra;


import com.example.dao.GroupDao;
import com.example.mapper.CourseMapper;
import com.example.mapper.StudentMapper;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  CourseMapper mapperCourse;
  @Autowired
  StudentMapper mapperStudent;
  @Value("${courses}")
  private int coursesNumber;

  @Autowired
  GroupDao groupDao;
  @Autowired
  private StudentService studentService;
  @Autowired
  CourseService courseService;
  @Autowired
  Random randomTest;
  @Autowired
  GroupService groupService;

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

  public boolean isExistStudent(Student student) {
    String sql = "SELECT * FROM students;";
    List<Student> students = jdbcTemplate.query(sql, mapperStudent);
    return students.contains(student);
  }

  public boolean isExistStudent(List<Student> list) {
    String sql = "SELECT * FROM students;";
    List<Student> students = studentService.getAll();
    var i = 0;
    for (Student stud : list) {
      if (students.contains(stud)) {
        i++;
      }
    }
    return list.size() == i;
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
    student.setGroupId(1);
    student.setFirstName("Amir");
    student.setLastName("Watson");
    student.setGroupName("nA-51");
    student.setCourse(courses);
    Student studentNext = new Student();
    studentNext.setGroupId(3);
    studentNext.setFirstName("Rex");
    studentNext.setLastName("Philip");
    studentNext.setGroupName("Jp-04");
    List<Student> studentListExpect = new ArrayList<>();
    studentListExpect.add(student);
    studentListExpect.add(studentNext);

    return studentListExpect;
  }

  @Value("${groups}")
  private int groupsNumber;
  @Value("${students-total}")
  private int studentsTotalNumber;

  public void createCourse() throws IOException, URISyntaxException {
    courseService.createData();
  }

  public void createGroup() {
    add(createGroupsList());
  }

  private void add(List<Group> groupList) {
    jdbcTemplate.batchUpdate("INSERT INTO groups (group_name) VALUES (?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, groupList.get(i).getName());
          }

          public int getBatchSize() {
            return groupList.size();
          }
        }
    );
  }

  private List<Group> createGroupsList() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0;
        i < Math.min(groupsNumber, studentsTotalNumber); i++) {
      groupList.add(new Group(groupName(randomTest, 2, 2)));
    }
    return groupList;
  }

  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.random(characters, 0, 0, true, false, null, random) + "-"
        + RandomStringUtils.random(digitCount, 0, 0, false, true, null, random);
  }


  public Student createStudent(int groupId, String firstName, String lastName,
      List<Course> courseList) {
    Student student = new Student();
    student.setGroupId(groupId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setCourse(courseList);
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
}
