package com.example.dao.test;

import com.example.dao.CourseDao;
import com.example.extra.TestUtils;
import com.example.model.Course;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("Test")
class CourseDaoTest {

  @Autowired
  CourseDao courseDao;
  @Autowired
  TestUtils utils;
  @BeforeEach
  void start() {
    utils.clearData();
  }
  @Test
  void add_ShouldAddCourseToDB() {
    String courseName = "Java";
    String courseDescription = "Java -  is a high-level, class-based,"
        + " object-oriented programming language that is designed to have "
        + "as few implementation dependencies as possible.";
    Course course = new Course(courseName,courseDescription);
    courseDao.add(course);
    Assertions.assertTrue(utils.isExistCourse(courseName, courseDescription));
  }


  @Test
  void add_ShouldAddCourseListToDB() {
    List<Course> courseList = new ArrayList<>();
    Course courseFirst = new Course("Pascal", "Pascal");
    courseFirst.setId(1);
    Course courseSecond = new Course("Python", "Python");
    courseSecond.setId(2);
    courseList.add(courseFirst);
    courseList.add(courseSecond);
    courseDao.add(courseList);
    Assertions.assertEquals(courseList, utils.getCourseList());
  }

  @Test
  void getAll_ShouldFindAllCoursesFromDB() {
    List<Course> courseList = new ArrayList<>();
    Course courseFirst = new Course("Pascal", "Pascal");
    courseFirst.setId(1);
    Course courseSecond = new Course("Python", "Python");
    courseSecond.setId(2);
    courseList.add(courseFirst);
    courseList.add(courseSecond);
    courseDao.add(courseList);
    Assertions.assertEquals(courseList, courseDao.getAll());
  }
}
