package com.example.resultTest;

import com.example.Data;
import com.example.Result;
import com.example.extra.TestUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = ResultTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
class ResultTest {

  @Autowired
  Data data;
  @Autowired
  TestUtils utils;
  @Autowired
  Result result;
  private final String separator = System.lineSeparator();

  @BeforeAll
  void start() throws IOException, URISyntaxException {
    utils.clearData();
    data.addAllData();
  }

  @AfterAll
  void end() {
    utils.clearData();
  }

  @Test
  void studentsWithCourse_ShouldFindStudentWithCourseNumber() {

    String expect = "2  | Bonnie      | Reynolds    | Maths  | Jp-04" + separator
        + "4  | Marie       | Zimmerman   | Maths  | cP-50" + separator
        + "5  | Evangeline  | Mcdonald    | Maths  | cP-50";
    Assertions.assertEquals(expect, result.studentsWithCourse("Maths"));
  }

  @Test
  void studentsWithOutCourse_ShouldFindStudentWithOutCourse() {
    String expect = "4  | Marie       | Zimmerman   ";
    Assertions.assertEquals(expect, result.studentsWithOutCourse(5));
  }

  @Test
  void studentsWhereCourseIsExists_ShouldFindStudentWhereCourseIsExists() {
    String expect = "1   | Bonnie       Stone       " + separator
        + "2   | Bonnie       Reynolds    " + separator
        + "3   | Alvin        Holland     " + separator
        + "4   | Marie        Zimmerman   " + separator
        + "5   | Evangeline   Mcdonald    ";
    Assertions.assertEquals(expect, result.getStudentsWhereCourseIsExists());
  }

  @Test
  void studentInfoPrint_ShouldPrintInfoAboutStudents() {
    String expect = "ID  | Firs name     Last name  " + separator
        + "----+--------------------------" + separator
        + "1   | Bonnie       Stone       " + separator
        + "2   | Bonnie       Reynolds    " + separator
        + "3   | Alvin        Holland     " + separator
        + "4   | Marie        Zimmerman   " + separator
        + "5   | Evangeline   Mcdonald    ";
    Assertions.assertEquals(expect, result.studentInfoPrint());
  }

  @Test
  void studentsCourse_ShouldFindStudentsCourse() {
    String expect = "1 | Bonnie  Stone |  3 |  History" + separator
        + "1 | Bonnie  Stone |  4 |  Probability theory" + separator
        + "1 | Bonnie  Stone |  5 |  Informatics";
    Assertions.assertEquals(expect, result.getStudentsCourse(1));
  }

  @Test
  void coursesInfo_ShouldFindGroupsWhereStudentsNumberLessOrEqualsNumber() {
    String expect = "1. Maths" + separator
        + "2. English" + separator
        + "3. History" + separator
        + "4. Probability theory" + separator
        + "5. Informatics";

    Assertions.assertEquals(expect, result.coursesInfo());
  }
}
