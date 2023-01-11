package com.example.dao.test;

import com.example.Data;
import com.example.dao.CourseDao;
import com.example.dao.StudentDao;
import com.example.extra.TestUtils;
import com.example.model.Course;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")
class StudentDaoTest {

  @Autowired
  TestUtils utils;
  @Autowired
  StudentDao studentDao;
  @Autowired
  CourseDao courseDao;
  @Autowired
 // @Qualifier("dataTest")
  Data data;

  @BeforeEach
  void start() throws IOException, URISyntaxException {
    data.addAllData();
  }

  @AfterEach
  void finish() {
    utils.clearData();
  }


  @Test
  void add_ShouldAddStudentToDB() {
    utils.clearStudent();
    List<Course> courseList = courseDao.getAll();
    Student student = utils.createStudent(2, "Max", "Smith", courseList);
    studentDao.add(student);
    Assertions.assertEquals(student, studentDao.getAll().get(studentDao.getAll().size() - 1));
  }

  @Test
  void add_ShouldAddStudentsListToDB() {
    utils.clearStudent();
    List<Student> studentList = utils.createStudentList(courseDao.getAll());
    studentDao.add(studentList);
    Assertions.assertEquals(studentList, studentDao.getAll());
  }

  @Test
  void getWithOutCourse_ShouldFindStudentsWithOutCourseByIdFromDB() {
    List<Course> courses = new ArrayList<>();
    String description = " Mathematics is the science that deals with the logic of shape, "
        + "quantity and arrangement";
    Course course = new Course("Maths", description);
    course.setCourseId(1);
    courses.add(course);
    Student student = new Student();
    student.setStudentId(4);
    student.setGroupId(4);
    student.setFirstName("Marie");
    student.setLastName("Zimmerman");
    student.setGroupName("cP-50");
    student.setCourseList(courses);
    List<Student> studentListExpect = new ArrayList<>();
    studentListExpect.add(student);
    Assertions.assertEquals(studentListExpect, studentDao.getWithOutCourse(5));
  }

  @Test
  void getWithCourse_ShouldFindStudentsWithCourseFromDB() {
    utils.clearStudent();
    List<Student> studentList = utils.createStudentList(courseDao.getAll());
    studentDao.add(studentList);
    List<Student> studentListExpect = new ArrayList<>();
    for (Student student : studentList) {
      if (!student.getCourseList().isEmpty() || student.getCourseList() == null) {
        studentListExpect.add(student);
      }
    }
    Assertions.assertEquals(studentListExpect, studentDao.getWithCourse());
  }

  @Test
  void addStudentsCourse_ShouldAddStudentsCourseToDB() {
    int courseId = 6;
    int studentId = 1;
    boolean exist = false;
    if (!utils.isExistStudentsCourse(studentId, courseId)) {
      studentDao.addStudentsCourse(studentId, courseId);
      exist = utils.isExistStudentsCourse(studentId, courseId);
    }
    Assertions.assertTrue(exist);

  }

  @Test
  void deleteFromCourse_ShouldDeleteStudentsCourseFromDB() {
    int courseId = 4;
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
    utils.clearStudent();
    List<Student> studentList = utils.createStudentList(courseDao.getAll());
    studentDao.add(studentList);
    Assertions.assertEquals(studentList, studentDao.getAll());
  }

  @Test
  void delete_ShouldDeleteStudentsByIdFromDB() {
    boolean exist = true;
    var id = 1;
    if (utils.isExistStudentId(id)) {
      studentDao.delete(id);
      exist = utils.isExistStudentId(id);
    }
    Assertions.assertFalse(exist);
  }

}
