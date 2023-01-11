package com.example.dao.test;


import com.example.Data;
import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@TestConfiguration
@PropertySource("classpath:application-test.properties")
public class StudentDaoTestConfig {

  @Bean
  @Primary
  @Scope("prototype")
  public Random randomTest() {
    return new Random(42);
  }

  @Bean
  @Scope("prototype")
  public Data dataTest() {
    return new Data(randomTest());
  }
}
