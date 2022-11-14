package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = DataTest.class)
@ActiveProfiles("test")
class DataTest {

  @Container
  public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer()
      //   .withPassword("test")
      //  .withUsername("test")
      .withInitScript("Table.sql");

  @DynamicPropertySource
  static void postgresqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }
  Data data;// new Data(new Random(42));
  private final String s = System.lineSeparator();
  //  private final JdbcTemplate jdbcTemplateTest = new JdbcTemplate(
//      new ContainerProperties().getDataSource(container));
  @Autowired
  public DataTest(Data data) {
    this.data = data;
  }
//  @BeforeEach
//  void start() throws IOException, URISyntaxException {
//
//    data.createAll();
//  }
//  @AfterEach
//  void end() {
//    clearData();
//  }

  //  @Test
//  void addStudents_ShouldAddedSetQuantityStudentsToDb() {
//    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
//    String expect = "1|3|Bonnie|Stone" + s
//        + "2|3|Bonnie|Reynolds" + s
//        + "3|3|Alvin|Holland" + s
//        + "4|4|Marie|Zimmerman" + s
//        + "5|4|Evangeline|Mcdonald";
//
//    List<Student> studentList =
//        jdbcTemplateTest.query("SELECT * FROM students;",
//            new BeanPropertyRowMapper<>(Student.class));
//    for (Student stud : studentList) {
//      stringJoiner.add(String.format("%d|%d|%s|%s",
//          stud.getStudentId(),
//          stud.getGroupId(),
//          stud.getFirstName(),
//          stud.getLastName()));
//    }
//    Assertions.assertEquals(expect, stringJoiner.toString());
//  }
//
  @Test
  void addCourses_ShouldAddedSetQuantityCoursesToDb() {
//    int courses = jdbcTemplateTest.queryForObject("SELECT COUNT (course_id) FROM courses;",
//        Integer.class);
//    Assertions.assertEquals(5, courses);
  }
//
//  @Test
//  void addGroups_ShouldAddedSetQuantityGroupsToDb() {
//    int groups = 0;
//    groups = jdbcTemplateTest.queryForObject("SELECT COUNT (group_id) FROM groups;",
//        Integer.class);
//    Assertions.assertEquals(groupsExpected, groups);
//  }
//
//  @Test
//  void addStudentsCourses_ShouldAddedStudentsCoursesToDb() {
//    String sql = "SELECT EXISTS (SELECT * FROM students_courses );";
//    boolean studentWithCourse = Boolean.TRUE.equals(jdbcTemplateTest.query(sql, rs -> {
//      if (rs.next()) {
//        return rs.getBoolean(1);
//      }
//      throw new IllegalStateException("Zero rows returned from the DB");
//    }));
//    Assertions.assertTrue(studentWithCourse);
//  }
}
