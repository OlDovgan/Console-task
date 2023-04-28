package com.example.dao;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import com.example.TestConfig;
import com.example.extra.TestUtils;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("Test")
class StudentDaoTest {

  @Value("${students-total}")
  private int studentsTotalNumber;

  @Autowired
  TestUtils utils;
  @Autowired
  StudentDao studentDao;
  @Autowired
  GroupDao groupDao;
  @Autowired
  CourseDao courseDao;

  @BeforeEach
  void start() throws IOException, URISyntaxException {
    utils.clearData();
    utils.createCourse();
 //   utils.createGroup();
  }

  @Test
  void add_ShouldAddStudentToDB() {
    Student student = new Student();
    student.setFirstName("Max");
    student.setLastName("Smith");
    studentDao.add(student);
    student.setId(1);
    Assertions.assertTrue(utils.isExistStudent(student));
  }

  @Test
  void add_ShouldAddStudentsListToDB() {
    List<Student> students = utils.createStudentList();
    studentDao.add(students);
    students.get(0).setId(1);
    students.get(1).setId(2);
    Assertions.assertTrue(utils.isExistStudent(students));
  }

  @Test
  void getWithOutCourse_ShouldFindStudentsWithOutCourseByIdFromDB() {

    List<Student> students = utils.createStudentList();
    studentDao.add(students);
    students.get(0).setId(1);
    students.get(1).setId(2);
    Assertions.assertEquals(students, studentDao.getWithOutCourse(1));
  }

  @Transactional
  @Test
  void getWithCourse_ShouldFindStudentsWithCourseFromDB() {
    // utils.clearData();
    List<Student> studentList = utils.createStudentList();
    //List<Course> courses = courseDao.getAll();
    studentList.get(0).setCourses(courseDao.getAll());
    studentDao.add(studentList);
    studentList.get(0).setId(1);
    studentList.get(1).setId(2);
    System.err.println("studentList " + studentList);
    List<Student> studentListExpect = new ArrayList<>();
    for (Student student : studentList) {
      if (student.getCourses() != null) {
        studentListExpect.add(student);
      }
    }
    System.err.println("1. studentList  " + studentList);
    System.err.println("2. studentDao.getWithCourse() " + studentListExpect);
    System.err.println("boolean " + studentList.equals(studentDao.getAll()));

    System.err.println("3. studentDao.getWithCourse() " + studentDao.getWithCourse());
    Assertions.assertEquals(studentListExpect, studentDao.getWithCourse());
  }

  @Test
  void addStudentsCourse_ShouldAddStudentsCourseToDB() throws IOException, URISyntaxException {
    utils.createStudentInDb();
    int courseId = 2;
    int studentId = 1;
    boolean exist = false;
    if (!utils.isExistStudentsCourse(studentId, courseId)) {
      studentDao.addStudentsCourse(studentId, courseId);
      exist = utils.isExistStudentsCourse(studentId, courseId);
    }
    Assertions.assertTrue(exist);
  }

  @Test
  void deleteFromCourse_ShouldDeleteStudentsCourseFromDB() throws IOException, URISyntaxException {
    utils.createStudentInDb();
    int courseId = 1;
    int studentId = 1;
    boolean exist = true;
    if (utils.isExistStudentsCourse(studentId, courseId)) {
      studentDao.deleteFromCourse(studentId, courseId);
      exist = utils.isExistStudentsCourse(studentId, courseId);
    }
    Assertions.assertFalse(exist);
  }

  @Test
  void getAll_ShouldFindAllStudentsFromDB() {
    List<Student> students = utils.createStudentList();
    studentDao.add(students);
    students.get(0).setId(1);
    students.get(1).setId(2);
    Assertions.assertEquals(students, studentDao.getAll());
  }

  @Test
  void delete_ShouldDeleteStudentsByIdFromDB() throws IOException, URISyntaxException {
    utils.createStudentInDb();
    boolean exist = true;
    var id = 2;
    if (utils.isExistStudentId(id)) {
      studentDao.deleteById(id);
      exist = utils.isExistStudentId(id);
    }
    Assertions.assertFalse(exist);
  }
}
