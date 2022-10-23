package com.example;

import static com.example.extra.TestUtils.clearData;
import static com.example.extra.TestUtils.isExistInStudents;
import static com.example.extra.TestUtils.isExistStudentId;
import static com.example.extra.TestUtils.isExistStudentWithCourse;

import com.example.testcontainers.config.ContainerProperties;
import com.example.testcontainers.config.ContainersEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class RequestTest extends ContainersEnvironment {

  public static final JdbcTemplate jdbcTemplateTest =
      new JdbcTemplate(new ContainerProperties().getDataSource(container));
  private final Request request = new Request(jdbcTemplateTest);
  private final String s = System.lineSeparator();
  private final Data data = new Data(new Random(42));

  @BeforeEach
  void start () throws IOException, URISyntaxException {
    data.createAll(jdbcTemplateTest);
  }
  @AfterEach
  void end() {
    clearData();
  }

  @Test
  void addStudent_ShouldAddStudentToDb() {
    request.addStudent("John", "Smite");
    Assertions.assertTrue(isExistInStudents( "John", "Smite"));

  }

  @Test
  void deleteStudent_ShouldDeleteStudentFromDb()
      throws SQLException {
    boolean before = isExistStudentId( 1);
    request.deleteStudent(1);
    Assertions.assertTrue(before && !isExistStudentId( 1));
  }

  @Test
  void addStudentToCourse_ShouldAddStudentToCourse() {
    boolean exist = false;
    if (!isExistStudentWithCourse(4, 4)) {
      request.addStudentToCourse(4, 4);
      exist=isExistStudentWithCourse(4, 4);
    }
    Assertions.assertTrue(exist);
  }

  @Test
  void deleteStudentFromCourse_ShouldDeleteStudentFromCourse() {
    boolean exist=true;
      if (isExistStudentWithCourse( 4, 2)) {
      request.deleteStudentFromCourse(4, 2);
      exist=isExistStudentWithCourse(4, 2);
    }
    Assertions.assertFalse(exist);
  }

    @Test
  void findStudentsWithCourse_ShouldFindStudentWithCourseNumber() {
    String expect = "2  | Bonnie      | Reynolds    | Maths  | Jp-04" + s
                  + "5  | Evangeline  | Mcdonald    | Maths  | cP-50";
    Assertions.assertEquals(expect, request.findStudentsWithCourse("Maths"));
  }

    @Test
  void findStudentsWithOutCourse_ShouldFindStudentWithOutCourse() {
    String expect = "2  | Bonnie      | Reynolds    " + s
                  + "3  | Alvin       | Holland     "+ s
                  + "5  | Evangeline  | Mcdonald    " ;
    Assertions.assertEquals(expect, request.findStudentsWithOutCourse(5));
  }

    @Test
  void studentsWhereCourseIsExists_ShouldFindStudentWhereCourseIsExists() {
      String expect = "1   | Bonnie       Stone       " + s
                    + "2   | Bonnie       Reynolds    " + s
                    + "3   | Alvin        Holland     " + s
                    + "4   | Marie        Zimmerman   " + s
                    + "5   | Evangeline   Mcdonald    ";
    Assertions.assertEquals(expect, request.studentsWhereCourseIsExists());
  }

    @Test
  void studentsWhereCourseIsNotExists_ShouldFindStudentWhereCourseIsNotExists() {
    Assertions.assertTrue(request.studentsWhereCourseIsNotExists(4, 6));
  }

   @Test
  void studentInfoPrint_ShouldPrintInfoAboutStudents() {
    String expect = "ID  | Firs name     Last name  " + s
        + "----+--------------------------" + s
        + "1   | Bonnie       Stone       " + s
        + "2   | Bonnie       Reynolds    " + s
        + "3   | Alvin        Holland     " + s
        + "4   | Marie        Zimmerman   " + s
        + "5   | Evangeline   Mcdonald    ";
    Assertions.assertEquals(expect, request.studentInfoPrint());
  }
    @Test
  void findCoursesOfStudent_ShouldFindStudentsCourse() {
    String expect = "1 | Bonnie  Stone |  4 |  Probability theory" + s
                  + "1 | Bonnie  Stone |  5 |  Informatics" + s
                  + "1 | Bonnie  Stone |  9 |  Hydrodynamics";
    Assertions.assertEquals(expect, request.findCoursesOfStudent(1));
  }

    @Test
  void findGroups_ShouldFindGroupsWhereStudentsNumberLessOrEqualsNumber() throws SQLException, IOException, URISyntaxException {
      data.createAll(jdbcTemplateTest);
    String expect = "2 | nA-51" + s
                  + "2 | cP-50";
    Assertions.assertEquals(expect, request.findGroups(2));
  }
}
