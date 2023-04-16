package com.example.StudentServiceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.TestConfig;
import com.example.dao.StudentDao;
import com.example.model.Course;
import com.example.model.Student;
import com.example.extra.TestUtils;
import com.example.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")

class StudentServiceTest {

  @Autowired
  StudentService studentService;
  @Autowired
  TestUtils utils;
  @MockBean
  StudentDao studentDao;

  @Test
  void getAll_ShouldCallStudentDaoMethodGetAll() {
    studentService.getAll();
    verify(studentDao, times(1)).getAll();
  }

  @Test
  void getWithCourse_ShouldCallStudentDaoMethodGetWithCourse() {
    studentService.getWithCourse();
    verify(studentDao, times(1)).getWithCourse();
  }

  @Test
  void getWithOutCourse_ShouldCallStudentDaoMethodGetWithOutCourse() {
    studentService.getWithOutCourse(1);
    verify(studentDao, times(1)).getWithOutCourse(1);
  }

  @Test
  void add_ShouldCallStudentDaoMethodAddForStudent() {
    List<Course> course = new ArrayList<>();
    Course courseFirst = new Course("Maths", "Maths");
    Course courseSecond = new Course("Java", "Java");
    course.add(courseFirst);
    course.add(courseSecond);
    Student student = utils.createStudent(2, "Max", "Smith", course);
    studentService.add(student);
    verify(studentDao, times(1)).add(student);
  }

  @Test
  void add_ShouldCallStudentDaoMethodAddForStudentList() {
    List<Course> course = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();
        Course courseFirst = new Course("Maths", "Maths");
    Course courseSecond = new Course("Java", "Java");
    course.add(courseFirst);
    course.add(courseSecond);
    studentList.add(utils.createStudent(2, "Max", "Smith", course));
    studentList.add(utils.createStudent(3, "Next", "Pit", course));
    studentService.add(studentList);
    verify(studentDao, times(1)).add(studentList);
  }

  @Test
  void delete_ShouldCallStudentDaoMethodDelete() {
    var id = 1;
    Mockito.doNothing().when(studentDao).deleteById(id);
    studentService.delete(id);
    verify(studentDao, times(1)).deleteById(id);
  }

  @Test
  void deleteFromCourse_ShouldCallStudentDaoMethodDeleteFromCourse() {
    int courseId = 2;
    int studentId = 2;
    Mockito.doNothing().when(studentDao).addStudentsCourse(studentId, courseId);
    studentService.deleteFromCourse(studentId, courseId);
    verify(studentDao, times(1)).deleteFromCourse(studentId, courseId);
  }
}
