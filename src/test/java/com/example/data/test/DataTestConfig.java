package com.example.data.test;



import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
@TestConfiguration
@PropertySource("classpath:application-test.properties")
@Profile("Test")
public class DataTestConfig {

  @Bean
  @Primary
  public Random randomTest() {
    return new Random(42);
  }
}
