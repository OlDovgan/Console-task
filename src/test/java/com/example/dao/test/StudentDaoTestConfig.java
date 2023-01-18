package com.example.dao.test;


import com.example.Data;
import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
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
  private GroupDao groupDao;
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private StudentDao studentDao;
  @Autowired
  private FileReader fileReader;

  @Bean
  @Primary
  @Scope("prototype")
  public Random randomTest() {
    return new Random(42);
  }

  @Bean
  @Primary
  @Scope("prototype")
  public Data data() {
    return new Data(randomTest(),groupDao,courseDao,studentDao,fileReader);
  }
}
