package com.example;

import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@ComponentScan("com.example")
//@ComponentScan(lazyInit = true)
@Profile("test")
public class DataTestConfig {
  @Bean
  @Primary
  public Random random() {
    return new Random(42);
  }

//  @Bean
//  @Primary
//  public Data data() {
//    return new Data(randomTest());
//  }

}
