package com.example;

import static com.example.extra.TestUtils.clearData;

import com.example.model.Student;
import com.example.testcontainers.config.ContainerProperties;
import com.example.testcontainers.config.ContainersEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class DataTest extends ContainersEnvironment {

  private final Data data = new Data(new Random(42));
  private final String s = System.lineSeparator();
  private final Settings settings = new Settings();
  private final Properties properties = settings.setProperties("Task.properties");
  private final JdbcTemplate jdbcTemplateTest = new JdbcTemplate(
      new ContainerProperties().getDataSource(container));
  private final int coursesExpected = Integer.valueOf(properties.getProperty("courses"));
  private final int groupsExpected = Integer.valueOf(properties.getProperty("groups"));

  @BeforeEach
  void start() throws IOException, URISyntaxException {
    data.createAll(jdbcTemplateTest);
  }

  @AfterEach
  void end() {
    clearData();
  }

  @Test
  @Autowired
  void addStudents_ShouldAddedSetQuantityStudentsToDb() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String expect = "1|3|Bonnie|Stone" + s
        + "2|3|Bonnie|Reynolds" + s
        + "3|3|Alvin|Holland" + s
        + "4|4|Marie|Zimmerman" + s
        + "5|4|Evangeline|Mcdonald";

    List<Student> studentList =
        jdbcTemplateTest.query("SELECT * FROM students;",
            new BeanPropertyRowMapper<>(Student.class));
    for (Student stud : studentList) {
      stringJoiner.add(String.format("%d|%d|%s|%s",
          stud.getStudentId(),
          stud.getGroupId(),
          stud.getFirstName(),
          stud.getLastName()));
    }
    Assertions.assertEquals(expect, stringJoiner.toString());
  }

  @Test
  void addCourses_ShouldAddedSetQuantityCoursesToDb() {
    int courses = 0;
    courses = jdbcTemplateTest.queryForObject("SELECT COUNT (course_id) FROM courses;",
        Integer.class);
    Assertions.assertEquals(coursesExpected, courses);
  }

  @Test
  void addGroups_ShouldAddedSetQuantityGroupsToDb() {
    int groups = 0;
    groups = jdbcTemplateTest.queryForObject("SELECT COUNT (group_id) FROM groups;",
        Integer.class);
    Assertions.assertEquals(groupsExpected, groups);
  }

  @Test
  void addStudentsCourses_ShouldAddedStudentsCoursesToDb() {
    boolean studentWithCourse = false;
    String sql = "SELECT EXISTS (SELECT * FROM students_courses );";
    studentWithCourse = Boolean.TRUE.equals(jdbcTemplateTest.query(sql, rs -> {
      if (rs.next()) {
        return rs.getBoolean(1);
      }
      throw new IllegalStateException("Zero rows returned from the DB");
    }));
    Assertions.assertTrue(studentWithCourse);
  }
}
