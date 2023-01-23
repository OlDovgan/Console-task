package com.example.service.test;

import com.example.dao.test.StudentDaoTestConfig;
import com.example.extra.TestUtils;
import com.example.service.CourseService;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")
@TestInstance(Lifecycle.PER_CLASS)
class CourseServiceTest {

  @Value("${courses}")
  private String coursesTest;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  CourseService courseService;

  @Autowired
  TestUtils utils;

  @Test
  void createData_ShouldAddedSetQuantityCoursesToDb() throws IOException, URISyntaxException {
    utils.clearData();
    courseService.createData();

    int courses = jdbcTemplate.queryForObject("SELECT COUNT (course_id) FROM courses;",
        Integer.class);
    Assertions.assertEquals(Integer.valueOf(coursesTest), courses);
  }


}
