package com.example.data.test;

import com.example.Data;
import com.example.extra.TestUtils;
import com.example.mapper.StudentMapper;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = DataTestConfig.class)
@ActiveProfiles("Test")
@TestInstance(Lifecycle.PER_CLASS)
class DataTest {

  @Autowired
  Data data;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Value("${courses}")
  private String coursesTest;
  @Value("${groups}")
  private String groupsTest;

  private final String separator = System.lineSeparator();
  @Autowired
  private TestUtils utils;
  @Autowired
  private StudentMapper mapper;

  DataTest() {
  }

  @BeforeAll
  void start() throws IOException, URISyntaxException {
    data.addAllData();
  }

  @AfterAll
  void end() {
    utils.clearData();
  }

  @Test
  void addStudents_ShouldAddedSetQuantityStudentsToDb() {

    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String expect = "1|3|Bonnie|Stone" + separator
        + "2|3|Bonnie|Reynolds" + separator
        + "3|3|Alvin|Holland" + separator
        + "4|4|Marie|Zimmerman" + separator
        + "5|4|Evangeline|Mcdonald";

    List<Student> studentList =
        jdbcTemplate.query("SELECT * FROM students;",
            mapper);
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
  void addCourses_ShouldAddedSetQuantityCoursesToDb() {

    int courses = jdbcTemplate.queryForObject("SELECT COUNT (course_id) FROM courses;",
        Integer.class);
    Assertions.assertEquals(Integer.valueOf(coursesTest), courses);
  }

  @Test
  void addGroups_ShouldAddedSetQuantityGroupsToDb() {
    int groups = 0;
    groups = jdbcTemplate.queryForObject("SELECT COUNT (group_id) FROM groups;",
        Integer.class);
    Assertions.assertEquals(Integer.valueOf(groupsTest), groups);
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
