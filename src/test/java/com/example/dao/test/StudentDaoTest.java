package com.example.dao.test;

import com.example.TestConfig;
import com.example.dao.CourseDao;
import com.example.dao.StudentDao;
import com.example.extra.TestUtils;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext
@ActiveProfiles("Test")
class StudentDaoTest {

  @Autowired
  TestUtils utils;
  @Autowired
  StudentDao studentDao;
  @Autowired
  CourseDao courseDao;

  @Test
  void add_ShouldAddStudentToDB() {
    utils.clearStudent();
    Student student = new Student();
    student.setGroupId(0);
    student.setFirstName("Max");
    student.setLastName("Smith");
    studentDao.add(student);
    student.setId(1);
    Assertions.assertTrue(studentDao.getAll().contains(student));
  }

  @Test
  void add_ShouldAddStudentsListToDB() {
    utils.clearStudent();
    studentDao.add(utils.createStudentList());
    Assertions.assertEquals(utils.createStudentList(), studentDao.getAll());
  }

  @Test
  void getWithOutCourse_ShouldFindStudentsWithOutCourseByIdFromDB() {
    utils.clearStudent();
    studentDao.add(utils.createStudentList());
    Assertions.assertEquals(utils.createStudentList(), studentDao.getWithOutCourse(1));
  }

  @Test
  void getWithCourse_ShouldFindStudentsWithCourseFromDB() {
    utils.clearStudent();
    List<Student> studentList = utils.createStudentList(courseDao.getAll());
    studentDao.add(studentList);
    List<Student> studentListExpect = new ArrayList<>();
    for (Student student : studentList) {
      if (!student.getCourse().isEmpty() || student.getCourse() == null) {
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
