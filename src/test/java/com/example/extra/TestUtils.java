package com.example.extra;


import com.example.dao.GroupDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  GroupDao groupDao;
  @Autowired
  @Qualifier("mapperCourse")
  RowMapper mapperCourse;
  @Autowired
  @Qualifier("mapperGroup")
  RowMapper mapperGroup;
  @Autowired
  @Qualifier("mapperStudent")
  RowMapper mapperStudent;

  public boolean isExistInStudents(String firstName, String lastName) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE students.first_name= ? "
        + " AND students.last_name= ?);";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, firstName, lastName));
  }

  public boolean isExistStudentWithCourse(int student_id, int course_id) {
    String sql =
        "SELECT EXISTS(SELECT * FROM students_courses WHERE students_courses.student_id= ? "
            + " AND students_courses.course_id= ? );";
    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, student_id, course_id));
  }

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

//  private Student findStudent(int id) {
//
//Student student = jdbcTemplate.queryForObject("SELECT * FROM students WHERE student_id= ? ;",
//    new Object[]{id}, Student.class);
//
//    List<Integer[]> studentsCoursesLinkFromDB = jdbcTemplate.query(
//        "SELECT * FROM students_courses WHERE student_id = 4",
//        (rs, rowNum) -> {
//          Integer[] studentsCourse = new Integer[2];
//          studentsCourse[0] = rs.getInt(1);
//          studentsCourse[1] = rs.getInt(2);
//          return studentsCourse;
//        });
//
//    List<Student> studentListNew = new ArrayList<>();
//    List<Course> courseListFromDB = courseDao.getAll();
//    for (Student student : studentList) {
//      setStudentsGroup(student);
//      setNewCourseList(student, studentsCoursesLinkFromDB, courseListFromDB);
//      studentListNew.add(student);
//    }
//    return student;
//  }


  public List<Course> getCourseList() {
    return jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id;",
        mapperCourse);
  }

  public List<Group> getGroupList() {
    return jdbcTemplate.query("SELECT * FROM groups ORDER BY group_id;",
        mapperGroup);
  }

  public List<Student> getStudentList() {
    List<Course> courseListFull = getCourseList();
    List<Student> studentList = jdbcTemplate.query("SELECT * FROM students;", mapperStudent);
    List<Group> groupList = getGroupList();
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
        if (stud.getGroupId() == group.getGroupId()) {
          stud.setGroupName(group.getGroupName());
        }
      }
      List<Course> courseListTemp = new ArrayList<>();
      for (Integer[] i : studentsCoursesLinks) {

        if (i[0].equals(stud.getStudentId())) {
          courseListTemp.add(courseListFull.get(i[1] - 1));
        }
        stud.setCourseList(courseListTemp);
      }
      studentListNew.add(stud);
    }
    return studentListNew;
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

  public boolean isExistStudent(String firstName, String lastName) {
    String sql = "SELECT EXISTS(SELECT * FROM students WHERE students.first_name= ? "
        + "AND students.last_name= ? );";

    return Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }, firstName, lastName));
  }

  public List<Student> createStudentList(List<Course> courseList ) {
    List<Student> studentList = new ArrayList<>();
    studentList.add(createStudent(3, "John", "Smith", courseList));
    studentList.add(createStudent( 2, "Linda", "Lopes", courseList));
    studentList.add(createStudent(1, "Max", "Smith", courseList));
    return studentList;
  }

  public Student createStudent( int groupId, String firstName, String lastName,
      List<Course> courseList) {
    Student student = new Student();
    student.setGroupId(groupId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setCourseList(courseList);
    String str =  jdbcTemplate.queryForObject(
        "SELECT group_name FROM groups WHERE group_id = ? ",new Object[]{student.getGroupId()},
        String.class);
    student.setGroupName(str);
    return student;
  }


  public void clearData() {
    jdbcTemplate.update(
        "TRUNCATE students, courses, groups, students_courses RESTART IDENTITY");
  }

  public void clearStudent() {
    jdbcTemplate.update(
        "TRUNCATE students, students_courses RESTART IDENTITY");
  }
}
