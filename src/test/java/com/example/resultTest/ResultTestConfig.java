package com.example.resultTest;


import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class ResultTestConfig {

  @Bean
  @Primary
  public Random randomTest() {
    return new Random(42);
  }
}
