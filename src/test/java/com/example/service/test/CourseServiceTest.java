package com.example.service.test;

import com.example.dao.CourseDao;
import com.example.TestConfig;
import com.example.extra.TestUtils;
import com.example.model.Course;
import com.example.service.CourseService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext
@ActiveProfiles("Test")
class CourseServiceTest {

  @Value("${courses}")
  private int coursesTest;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  CourseService courseService;

  @Autowired
  TestUtils utils;
  @Autowired
  CourseDao courseDao;

  @Test
  void createData_ShouldAddedSetQuantityCoursesToDb() throws IOException, URISyntaxException {
    courseService.createNewData();
    int courses = jdbcTemplate.queryForObject("SELECT COUNT (course_id) FROM courses;",
        Integer.class);
    Assertions.assertEquals(coursesTest, courses);
  }

  @Test
  void getAll_ShouldFindAllCoursesFromDB() {
    utils.clearData();
    List<Course> courseList = new ArrayList<>();
    Course courseFirst = new Course("Pascal", "Pascal");
    courseFirst.setId(1);
    Course courseSecond = new Course("Python", "Python");
    courseSecond.setId(2);
    courseList.add(courseFirst);
    courseList.add(courseSecond);
    courseDao.add(courseList);
    Assertions.assertEquals(courseList, courseService.getAll());
  }
}
