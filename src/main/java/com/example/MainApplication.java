package com.example;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class
MainApplication {

  public static void main(String[] args) {
    try {
      SpringApplication.run(MainApplication.class, args);
    } catch (Exception exception) {
    }
  }
}
