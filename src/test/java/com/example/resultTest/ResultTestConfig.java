package com.example.resultTest;

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
@PropertySource("classpath:application-test.properties")
public class ResultTestConfig {

  @Bean
  @Primary
  public Random randomTest() {
    return new Random(42);
  }

  GroupDao groupDao;
  CourseDao courseDao;
  StudentDao studentDao;
  FileReader fileReaderTest;

  @Bean
  @Autowired
  public Data testData() {
    return new Data(randomTest(), groupDao, courseDao, studentDao, fileReaderTest);
  }
}
