package com.example.resultTest;


import com.example.Result;
import com.example.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext
class ResultTest {

  @Autowired
  Result result;
  private final String separator = System.lineSeparator();

  @Test
  void studentsWithCourse_ShouldFindStudentWithCourseNumber() {

    String expect =
        "1  | Adele       | Reilly      | Maths  | nA-51" + separator
            + "3  | Gabrielle   | Ferguson    | Maths  | nA-51" + separator
            + "4  | Nicolas     | Stone       | Maths  | cP-50" + separator
            + "5  | Rufus       | Zimmerman   | Maths  | cP-50";

    Assertions.assertEquals(expect, result.studentsWithCourse("Maths"));
  }

  @Test
  void studentsWithOutCourse_ShouldFindStudentWithOutCourse() {
    String expect = "2  | Amir        | Watson      ";
    Assertions.assertEquals(expect, result.studentsWithOutCourse(1));
  }

  @Test
  void studentsWhereCourseIsExists_ShouldFindStudentWhereCourseIsExists() {
    String expect =
        "1   | Adele        Reilly      " + separator
            + "2   | Amir         Watson      " + separator
            + "3   | Gabrielle    Ferguson    " + separator
            + "4   | Nicolas      Stone       " + separator
            + "5   | Rufus        Zimmerman   ";

    Assertions.assertEquals(expect, result.getStudentsWhereCourseIsExists());
  }

  @Test
  void studentInfoPrint_ShouldPrintInfoAboutStudents() {
    String expect = "ID  | Firs name     Last name  " + separator
        + "----+--------------------------" + separator
        + "1   | Adele        Reilly      " + separator
        + "2   | Amir         Watson      " + separator
        + "3   | Gabrielle    Ferguson    " + separator
        + "4   | Nicolas      Stone       " + separator
        + "5   | Rufus        Zimmerman   ";
    Assertions.assertEquals(expect, result.studentInfoPrint());
  }

  @Test
  void studentsCourse_ShouldFindStudentsCourse() {
    String expect = "1 | Adele  Reilly |  1 |  Maths" + separator
        + "1 | Adele  Reilly |  5 |  Informatics";
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
