package com.example;

import static com.example.extra.TestUtils.clearData;

import com.example.testcontainers.config.ContainerProperties;
import com.example.testcontainers.config.ContainersEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataTest extends ContainersEnvironment {

  private final Data data = new Data(new Random(42));
  private final String s = System.lineSeparator();
  private  final Settings settings = new Settings();
  private final Properties properties = settings.properties("Task.properties");
  private static final JDBC jdbc = new JDBC(new ContainerProperties().getProperties(container));
  private final int studentTotalExpected = Integer.valueOf(
      properties.getProperty("students-total"));
  private final int coursesExpected = Integer.valueOf(properties.getProperty("courses"));
  private final int groupsExpected = Integer.valueOf(properties.getProperty("groups"));

  @BeforeEach
  void start () throws SQLException, IOException, URISyntaxException {
    data.createAll(jdbc);
  }
  @AfterEach
  void end () throws SQLException {
  clearData(jdbc) ;
  }

  @Test
  void addStudents_ShouldAddedSetQuantityStudentsToDb()
      throws SQLException {
    String expect = "1|3|Bonnie|Stone" + s
                  + "2|3|Bonnie|Reynolds" + s
                  + "3|3|Alvin|Holland" + s
                  + "4|4|Marie|Zimmerman" + s
                  + "5|4|Evangeline|Mcdonald";

    try (Statement statement = jdbc.getDbConnection().createStatement()) {
      ResultSet studentResult = statement
          .executeQuery("SELECT * FROM students;");
      StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
      while (studentResult.next()) {
        stringJoiner.add(String.format("%d|%d|%s|%s",
            studentResult.getInt(1),
            studentResult.getInt(2),
            studentResult.getString(3),
            studentResult.getString(4)));
      }
      Assertions.assertEquals(expect, stringJoiner.toString());
    }
  }


  @Test
  void addCourses_ShouldAddedSetQuantityCoursesToDb()
      throws SQLException{
    int courses = 0;
    try (Statement statement = jdbc.getDbConnection().createStatement()) {
      ResultSet coursesResult = statement
          .executeQuery("SELECT COUNT (course_id) FROM courses;");
      if (coursesResult.next()) {
        courses = coursesResult.getInt(1);
      }
    }
    Assertions.assertEquals(coursesExpected, courses);
  }

  @Test
  void addGroups_ShouldAddedSetQuantityGroupsToDb()
      throws SQLException {
    int groups = 0;
    try (Statement statement = jdbc.getDbConnection().createStatement()) {
      ResultSet groupsResult = statement
          .executeQuery("SELECT COUNT (group_id) FROM groups;");
      if (groupsResult.next()) {
        groups = groupsResult.getInt(1);
      }
    }
    Assertions.assertEquals(groupsExpected, groups);
  }

  @Test
  void addStudentsCourses_ShouldAddedStudentsCoursesToDb()
      throws SQLException {
    boolean studentWithCourse;
    try (Statement statement = jdbc.getDbConnection().createStatement()) {
      ResultSet studentsCoursesResult = statement
          .executeQuery("SELECT EXISTS (SELECT * FROM students_courses );");
      studentWithCourse = false;
      if (studentsCoursesResult.next()) {
        studentWithCourse = studentsCoursesResult.getBoolean(1);
      }
    }
    Assertions.assertTrue(studentWithCourse);
  }
}
