package com.example;

import static com.example.extra.TestUtils.clearData;
import static com.example.extra.TestUtils.isExistInStudents;
import static com.example.extra.TestUtils.isExistStudentId;
import static com.example.extra.TestUtils.isExistStudentWithCourse;
import com.example.dao.StudentDao;
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

class RequestTest extends ContainersEnvironment {


  private final JDBC jdbc = new JDBC(new ContainerProperties().getProperties(container));
  private final StudentDao studentDao = new StudentDao();
  private final Request request = new Request(jdbc, studentDao);
  private final String s = System.lineSeparator();
  private final Data data = new Data(new Random(42));

  @BeforeEach
  void start () throws SQLException, IOException, URISyntaxException {
    data.createAll(jdbc);
  }
  @AfterEach
  void end() throws SQLException {
    clearData(jdbc);
  }

  @Test
  void addStudent_ShouldAddStudentToDb() throws SQLException {
    request.addStudent("John", "Smite");
    Assertions.assertTrue(isExistInStudents(jdbc, "John", "Smite"));

  }

  @Test
  void deleteStudent_ShouldDeleteStudentFromDb()
      throws SQLException {
    boolean before = isExistStudentId(jdbc, 1);
    request.deleteStudent(1);
    Assertions.assertTrue(before && !isExistStudentId(jdbc, 1));
  }

  @Test
  void addStudentToCourse_ShouldAddStudentToCourse()
      throws SQLException {
    boolean exist = false;
    if (!isExistStudentWithCourse(jdbc, 4, 4)) {
      request.addStudentToCourse(4, 4);
      exist=isExistStudentWithCourse(jdbc, 4, 4);
    }
    Assertions.assertTrue(exist);
  }

  @Test
  void deleteStudentFromCourse_ShouldDeleteStudentFromCourse()
      throws SQLException {
    boolean exist=true;
      if (isExistStudentWithCourse(jdbc, 4, 2)) {
      request.deleteStudentFromCourse(4, 2);
      exist=isExistStudentWithCourse(jdbc, 4, 2);
    }
    Assertions.assertFalse(exist);
  }

    @Test
  void findStudentsWithCourse_ShouldFindStudentWithCourse()
        throws SQLException {
    String expect = "1  | Bonnie      | Stone       " + s
                  + "2  | Bonnie      | Reynolds    " + s
                  + "3  | Alvin       | Holland     " +s
                  + "5  | Evangeline  | Mcdonald    ";
    Assertions.assertEquals(expect, request.findStudentsWithOutCourse(3));
  }

    @Test
  void findStudentsWithOutCourse_ShouldFindStudentWithOutCourse()
        throws SQLException {
    String expect = "2  | Bonnie      | Reynolds    " + s
                  + "3  | Alvin       | Holland     "+ s
                  + "5  | Evangeline  | Mcdonald    " ;
    Assertions.assertEquals(expect, request.findStudentsWithOutCourse(5));
  }

    @Test
  void studentsWhereCourseIsExists_ShouldFindStudentWhereCourseIsExists()
        throws SQLException {
      String expect = "1   | Bonnie       Stone       " + s
                    + "2   | Bonnie       Reynolds    " + s
                    + "3   | Alvin        Holland     " + s
                    + "4   | Marie        Zimmerman   " + s
                    + "5   | Evangeline   Mcdonald    ";
    Assertions.assertEquals(expect, request.studentsWhereCourseIsExists());
  }

    @Test
  void studentsWhereCourseIsNotExists_ShouldFindStudentWhereCourseIsNotExists()
        throws SQLException {
    Assertions.assertTrue(request.studentsWhereCourseIsNotExists(4, 6));
  }

   @Test
  void studentInfoPrint_ShouldPrintInfoAboutStudents()
       throws SQLException {
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
  void findCoursesOfStudent_ShouldFindStudentsCourse()
        throws SQLException {
    String expect = "4 | Marie  Zimmerman |  2 |  English " + s
                  + "4 | Marie  Zimmerman |  3 |  History " + s
                  + "4 | Marie  Zimmerman |  5 |  Informatics ";
    Assertions.assertEquals(expect, request.findCoursesOfStudent(4));
  }

    @Test
  void findGroups_ShouldFindGroupsWhereStudentsNumberLessOrEqualsNumber() throws SQLException, IOException, URISyntaxException {
      data.createAll(jdbc);
    String expect = "2 | nA-51" + s
                  + "2 | cP-50";
    Assertions.assertEquals(expect, request.findGroups(2));
  }
}
