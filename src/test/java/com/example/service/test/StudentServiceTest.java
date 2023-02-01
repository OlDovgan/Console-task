package com.example.service.test;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

import com.example.TestConfig;
import com.example.extra.TestUtils;
import com.example.model.Course;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext(classMode = BEFORE_CLASS)
@ActiveProfiles("Test")

public class StudentServiceTest {

  @Autowired
  StudentService studentService;
  @Autowired
  CourseService courseService;
  @Autowired
  TestUtils utils;
  @Test
  void getAll_ShouldGetStudentsFromDb() {
    utils.clearStudent();
    List<Student> studentList = utils.createStudentList(courseService.getAll());
    studentService.add(studentList);
    Assertions.assertEquals(studentList, studentService.getAll());
  }

@Test
  void getWithCourse_ShouldFindStudentsWithCourseFromDB() {
  utils.clearStudent();
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
    utils.clearStudent();
    studentService.add(utils.createStudentList());
    Assertions.assertEquals(utils.createStudentList(), studentService.getWithOutCourse(1));
  }
  @Test
  void add_ShouldAddStudentToDB() {
    utils.clearStudent();
    List<Course> courseList = courseService.getAll();
    Student student = utils.createStudent(2, "Max", "Smith", courseList);
    studentService.add(student);
    Assertions.assertEquals(student, studentService.getAll().get(studentService.getAll().size() - 1));
  }
  @Test
  void add_ShouldAddStudentsListToDB() {
    utils.clearStudent();
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
}
