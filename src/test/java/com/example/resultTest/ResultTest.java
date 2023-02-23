package com.example.resultTest;

import com.example.Result;
import com.example.TestConfig;
import com.example.service.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")
class ResultTest {

  @MockBean
  Result result;
  @MockBean
  Data data;
  private final String separator = System.lineSeparator();

  @Test
  void studentsWithCourse_ShouldFindStudentWithCourseNumber() {
    String expect =
        "1  | Adele       | Reilly      | Maths  | nA-51" + separator
            + "3  | Gabrielle   | Ferguson    | Maths  | nA-51" + separator
            + "4  | Nicolas     | Stone       | Maths  | cP-50" + separator
            + "5  | Rufus       | Zimmerman   | Maths  | cP-50";
    Mockito.when(result.studentsWithCourse("Maths")).thenReturn(expect);

    Assertions.assertEquals(expect, result.studentsWithCourse("Maths"));
  }

  @Test
  void studentsWithOutCourse_ShouldFindStudentWithOutCourse() {
    String expect = "2  | Amir        | Watson      ";
    Mockito.when(result.studentsWithOutCourse(1)).thenReturn(expect);

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
    Mockito.when(result.getStudentsWhereCourseIsExists()).thenReturn(expect);

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
    Mockito.when(result.studentInfoPrint()).thenReturn(expect);

    Assertions.assertEquals(expect, result.studentInfoPrint());
  }

  @Test
  void getStudentsCourse_ShouldFindStudentsCourse() {
    String expect =
        "1   | Tabitha  Richardson |  8 |  Theory of machines and mechanisms" + separator
            + "1   | Tabitha  Richardson |  4 |  Probability theory" + separator
            + "1   | Tabitha  Richardson |  5 |  Informatics";
    Mockito.when(result.getStudentsCourse(1)).thenReturn(expect);

    Assertions.assertEquals(expect, result.getStudentsCourse(1));
  }

  @Test
  void coursesInfo_ShouldFindGroupsWhereStudentsNumberLessOrEqualsNumber() {
    String expect = "1. Maths" + separator
        + "2. English" + separator
        + "3. History" + separator
        + "4. Probability theory" + separator
        + "5. Informatics";
    Mockito.when(result.coursesInfo()).thenReturn(expect);

    Assertions.assertEquals(expect, result.coursesInfo());
  }
}
