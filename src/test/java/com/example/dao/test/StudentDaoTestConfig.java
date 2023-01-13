package com.example.dao.test;


import com.example.Data;
import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class StudentDaoTestConfig {

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
    return new Data(randomTest());
  }
}
