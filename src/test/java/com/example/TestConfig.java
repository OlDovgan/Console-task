package com.example;



import com.example.layer.service.AutoRun;
import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class TestConfig {

  @Bean
  @Primary
  @Scope("prototype")
  public Random randomTest() {
    return new Random(42);
  }
  @MockBean
  AutoRun autoRun;

}
