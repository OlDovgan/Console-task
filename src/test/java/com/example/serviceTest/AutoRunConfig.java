package com.example.serviceTest;

import com.example.layer.service.AutoRun;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class AutoRunConfig {
  @Bean
  public AutoRun autoRun() {
    return new AutoRun();
  }
}
