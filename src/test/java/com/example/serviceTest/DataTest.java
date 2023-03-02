package com.example.serviceTest;

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

@SpringBootTest(classes = StopConfig.class)
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
    verify(groupService, times(0)).getAll();
    verify(studentService, times(0)).getAll();
  }
}
