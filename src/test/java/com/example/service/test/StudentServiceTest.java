package com.example.service.test;

import com.example.dao.test.StudentDaoTestConfig;
import com.example.extra.TestUtils;
import com.example.model.Course;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.Data;
import com.example.service.StudentService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")

public class StudentServiceTest {

  @Autowired
  StudentService studentService;
  @Autowired
  CourseService courseService;
  @Autowired
  Data data;
  @Autowired
  TestUtils utils;

  @BeforeEach
  void start() throws IOException, URISyntaxException {
      data.createAll();
  }

  @AfterEach
  void finish() {
    data.clearAll();
  }

  @Test
  void getAll_ShouldAddedStudentsToDb() {

    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String separator = System.lineSeparator();
    String expect = "1|1|Adele|Reilly" + separator
        + "2|1|Amir|Watson" + separator
        + "3|1|Gabrielle|Ferguson" + separator
        + "4|4|Nicolas|Stone" + separator
        + "5|4|Rufus|Zimmerman";

    for (Student stud : studentService.getAll()) {
      stringJoiner.add(String.format("%d|%d|%s|%s",
          stud.getId(),
          stud.getGroupId(),
          stud.getFirstName(),
          stud.getLastName()));
    }
    Assertions.assertEquals(expect, stringJoiner.toString());
  }

@Test
  void getWithCourse_ShouldFindStudentsWithCourseFromDB() {
  studentService.clear();
  List<Student> studentList = utils.createStudentList(courseService.getAll());
  studentService.add(studentList);
  List<Student> studentListExpect = new ArrayList<>();
  for (Student student : studentList) {
    if (!student.getCourse().isEmpty() || student.getCourse() == null) {
      studentListExpect.add(student);
    }
  }
  Assertions.assertEquals(studentListExpect, studentService.getWithCourse());

}
  @Test
  void getWithOutCourse() {
    List<Course> courses = new ArrayList<>();
    String description = " English is a language—originally the language of the people of England";
    String description2 = " Probability theory is the branch of mathematics concerned with probability";
    Course first = new Course("English", description);
    Course second = new Course("Probability theory", description2);
    first.setId(2);
    second.setId(4);
    courses.add(first);
    courses.add(second);
    Student student = new Student();
    student.setId(2);
    student.setGroupId(1);
    student.setFirstName("Amir");
    student.setLastName("Watson");
    student.setGroupName("nA-51");
    student.setCourse(courses);
    List<Student> studentListExpect = new ArrayList<>();
    studentListExpect.add(student);
    Assertions.assertEquals(studentListExpect, studentService.getWithOutCourse(1));
  }
  @Test
  void add_ShouldAddStudentToDB() {
    studentService.clear();
    List<Course> courseList = courseService.getAll();
    Student student = utils.createStudent(2, "Max", "Smith", courseList);
    studentService.add(student);
    Assertions.assertEquals(student, studentService.getAll().get(studentService.getAll().size() - 1));
  }
  @Test
  void add_ShouldAddStudentsListToDB() {
    studentService.clear();
    List<Student> studentList = utils.createStudentList(courseService.getAll());
    studentService.add(studentList);
    Assertions.assertEquals(studentList, studentService.getAll());
  }
  @Test
  void delete_ShouldDeleteStudentsByIdFromDB(){
    boolean exist = true;
    var id = 1;
    if (utils.isExistStudentId(id)) {
      studentService.delete(id);
      exist = utils.isExistStudentId(id);
    }
    Assertions.assertFalse(exist);
  }
  @Test
  void deleteFromCourse_ShouldDeleteStudentsCourseFromDB() {
    int courseId = 2;
    int studentId = 2;
    boolean exist = true;
    if (utils.isExistStudentsCourse(studentId, courseId)) {
      studentService.deleteFromCourse(studentId, courseId);
      exist = utils.isExistStudentsCourse(studentId, courseId);
    }

    Assertions.assertFalse(exist);
  }
  @Test
  void addStudentsCourse_ShouldAddStudentsCourseToDB() {
    int courseId = 6;
    int studentId = 1;
    boolean exist = false;
    if (!utils.isExistStudentsCourse(studentId, courseId)) {
      studentService.addStudentsCourse(studentId, courseId);
      exist = utils.isExistStudentsCourse(studentId, courseId);
    }
    Assertions.assertTrue(exist);
  }
  @Test
  void clear_ShouldDeleteDataFromCourses() {
    boolean exist= false;
    if (!studentService.getAll().isEmpty()) {
      studentService.clear();
      exist=true;
    }

    Assertions.assertTrue(exist);
  }
}
