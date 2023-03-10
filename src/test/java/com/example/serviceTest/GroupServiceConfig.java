package com.example.serviceTest;


import com.example.layer.dao.GroupDao;
import com.example.layer.service.GroupService;
import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class GroupServiceConfig {

  @SpyBean
  GroupDao groupDao;

  @Bean
  public Random random() {
    return new Random();
  }

  @Bean
  public GroupService groupService() {
    return new GroupService(groupDao, random());
  }
}
