package com.example.resultTest;

<<<<<<< HEAD
import com.example.Data;
import com.example.Result;
import com.example.extra.TestUtils;
=======

import com.example.Result;
import com.example.dao.test.StudentDaoTestConfig;
import com.example.extra.TestUtils;
import com.example.service.Data;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
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
<<<<<<< HEAD
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = ResultTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
=======

@SpringBootTest(classes = StudentDaoTestConfig.class)
//@TestInstance(Lifecycle.PER_CLASS)
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
class ResultTest {

  @Autowired
  Data data;
  @Autowired
  TestUtils utils;
  @Autowired
  Result result;
  private final String separator = System.lineSeparator();

<<<<<<< HEAD
  @BeforeAll
  void start() throws IOException, URISyntaxException {
    utils.clearData();
    data.addAllData();
  }

  @AfterAll
  void end() {
    utils.clearData();
  }
=======
//  @BeforeAll
//  void start() throws IOException, URISyntaxException {
//  //  utils.clearData();
//  //  data.createAll();
//  }
//
//  @AfterAll
//  void end() {
//    utils.clearData();
//  }
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)

  @Test
  void studentsWithCourse_ShouldFindStudentWithCourseNumber() {

<<<<<<< HEAD
    String expect = "2  | Bonnie      | Reynolds    | Maths  | Jp-04" + separator
        + "4  | Marie       | Zimmerman   | Maths  | cP-50" + separator
        + "5  | Evangeline  | Mcdonald    | Maths  | cP-50";
=======
    String expect =
          "1  | Adele       | Reilly      | Maths  | nA-51" + separator
        + "3  | Gabrielle   | Ferguson    | Maths  | nA-51" + separator
        + "4  | Nicolas     | Stone       | Maths  | cP-50" + separator
        + "5  | Rufus       | Zimmerman   | Maths  | cP-50";
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    Assertions.assertEquals(expect, result.studentsWithCourse("Maths"));
  }

  @Test
  void studentsWithOutCourse_ShouldFindStudentWithOutCourse() {
<<<<<<< HEAD
    String expect = "4  | Marie       | Zimmerman   ";
    Assertions.assertEquals(expect, result.studentsWithOutCourse(5));
=======
    String expect = "2  | Amir        | Watson      ";
    Assertions.assertEquals(expect, result.studentsWithOutCourse(1));
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  }

  @Test
  void studentsWhereCourseIsExists_ShouldFindStudentWhereCourseIsExists() {
<<<<<<< HEAD
    String expect = "1   | Bonnie       Stone       " + separator
        + "2   | Bonnie       Reynolds    " + separator
        + "3   | Alvin        Holland     " + separator
        + "4   | Marie        Zimmerman   " + separator
        + "5   | Evangeline   Mcdonald    ";
=======
    String expect =
          "1   | Adele        Reilly      " + separator
        + "2   | Amir         Watson      " + separator
        + "3   | Gabrielle    Ferguson    " + separator
        + "4   | Nicolas      Stone       " + separator
        + "5   | Rufus        Zimmerman   ";
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    Assertions.assertEquals(expect, result.getStudentsWhereCourseIsExists());
  }

  @Test
  void studentInfoPrint_ShouldPrintInfoAboutStudents() {
    String expect = "ID  | Firs name     Last name  " + separator
        + "----+--------------------------" + separator
<<<<<<< HEAD
        + "1   | Bonnie       Stone       " + separator
        + "2   | Bonnie       Reynolds    " + separator
        + "3   | Alvin        Holland     " + separator
        + "4   | Marie        Zimmerman   " + separator
        + "5   | Evangeline   Mcdonald    ";
=======
        + "1   | Adele        Reilly      " + separator
        + "2   | Amir         Watson      " + separator
        + "3   | Gabrielle    Ferguson    " + separator
        + "4   | Nicolas      Stone       " + separator
        + "5   | Rufus        Zimmerman   ";
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
    Assertions.assertEquals(expect, result.studentInfoPrint());
  }

  @Test
  void studentsCourse_ShouldFindStudentsCourse() {
<<<<<<< HEAD
    String expect = "1 | Bonnie  Stone |  3 |  History" + separator
        + "1 | Bonnie  Stone |  4 |  Probability theory" + separator
        + "1 | Bonnie  Stone |  5 |  Informatics";
=======
    String expect = "1 | Adele  Reilly |  1 |  Maths" + separator
        + "1 | Adele  Reilly |  5 |  Informatics";
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
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
