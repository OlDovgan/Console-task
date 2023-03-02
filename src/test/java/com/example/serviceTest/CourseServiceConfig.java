package com.example.serviceTest;


import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.service.AutoRun;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class CourseServiceConfig {

  @SpyBean
  FileReader fileReader;
  @SpyBean
  CourseDao courseDao;
  @Value("${course-name-file}")
  String fileName;
  @Value("${courses}") int coursesNumber;

  @Bean
  public CourseService courseService() {
    return new CourseService(fileReader, courseDao,coursesNumber , fileName);
  }
  @MockBean
  AutoRun autoRun;
}
