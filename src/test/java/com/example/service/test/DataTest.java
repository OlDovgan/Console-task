package com.example.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.menu.AppMenu;
import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("Test")

public class DataTest {

  @MockBean
  CourseService courseService;
  @MockBean
  GroupService groupService;
  @MockBean
  StudentService studentService;
  @MockBean
  AppMenu appMenu;

  @Test
  void createData_ShouldCreateData() {
    verify(courseService, times(1)).getAll();
    verify(groupService, times(1)).getAll();
    verify(studentService, times(1)).getAll();
  }
}
