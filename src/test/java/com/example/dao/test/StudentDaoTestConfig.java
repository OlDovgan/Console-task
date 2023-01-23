package com.example.dao.test;


import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.service.CourseService;
import com.example.service.Data;
import com.example.service.GroupService;
import com.example.service.StudentService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class StudentDaoTestConfig {

  @Autowired
  GroupDao groupDao;
  @Autowired
  CourseDao courseDao;
  @Autowired
  StudentDao studentDao;
  @Autowired
  FileReader fileReader;

  @Bean
  @Primary
  @Scope("prototype")
  public Random randomTest() {
    return new Random(42);
  }

  @Autowired
  CourseService courseService;

  @Bean
  @Primary
  @Scope("prototype")
  public GroupService groupService() {
    return new GroupService(groupDao, randomTest(), fileReader);
  }

  @Bean
  @Primary
  @Scope("prototype")
  public StudentService studentService() {
    return new StudentService(fileReader, courseDao, groupDao, studentDao, randomTest());
  }

  @Bean
  @Primary
  @Scope("prototype")
  public Data data() {
    return new Data(courseService, groupService(), studentService());
  }
}
