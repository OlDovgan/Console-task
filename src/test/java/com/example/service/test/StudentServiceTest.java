package com.example.service.test;

import com.example.dao.test.StudentDaoTestConfig;
import com.example.extra.TestUtils;
import com.example.mapper.StudentMapper;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.Data;
import com.example.service.GroupService;
import com.example.service.StudentService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")

public class StudentServiceTest {

  @Autowired
  TestUtils utils;

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  StudentMapper mapper;
  @Autowired
  StudentService studentService;
  @Autowired
  GroupService groupService;
  @Autowired
  CourseService courseService;
  @Autowired
  Data data;

  @BeforeEach
  void start() throws IOException, URISyntaxException {
    data.createAll();
  }

  @AfterEach
  void finish() {
    utils.clearData();
  }

  @Test
  void addStudents_ShouldAddedSetQuantityStudentsToDb() {

    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String separator = System.lineSeparator();
    String expect = "1|1|Adele|Reilly" + separator
        + "2|1|Amir|Watson" + separator
        + "3|1|Gabrielle|Ferguson" + separator
        + "4|4|Nicolas|Stone" + separator
        + "5|4|Rufus|Zimmerman";

    List<Student> studentList =
        jdbcTemplate.query("SELECT * FROM students;", mapper);
    for (Student stud : studentList) {
      stringJoiner.add(String.format("%d|%d|%s|%s",
          stud.getId(),
          stud.getGroupId(),
          stud.getFirstName(),
          stud.getLastName()));
    }
    Assertions.assertEquals(expect, stringJoiner.toString());
  }

  @Test
  void addStudentsCourses_ShouldAddedStudentsCoursesToDb() {
    String sql = "SELECT EXISTS (SELECT * FROM students_courses );";
    boolean studentWithCourse = Boolean.TRUE.equals(jdbcTemplate.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }));
    Assertions.assertTrue(studentWithCourse);
  }

}
