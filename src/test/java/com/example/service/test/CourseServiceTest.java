package com.example.service.test;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.dao.CourseDao;
import com.example.TestConfig;
import com.example.model.Course;
import com.example.service.CourseService;
import com.example.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")
class CourseServiceTest {

  @Value("${courses}")
  private int coursesTest;
  @Autowired
  CourseService courseService;
  @MockBean
  CourseDao courseDao;
  @MockBean
  StudentService studentService;

  @Test
  void createData_ShouldAddedSetQuantityCoursesToDb()  {
    List<Course> list = new ArrayList<>();
    for (int i = 0; i < coursesTest; i++) {
      list.add(null);
    }
    Mockito.when(courseDao.getAll()).thenReturn(list);
    Assertions.assertEquals(coursesTest, courseService.getAll().size());

  }

  @Test
  void getAll_ShouldCallCourseDaoMethodGetAll()  {
    courseService.getAll();
    verify(courseDao, times(1)).getAll();
  }
}
