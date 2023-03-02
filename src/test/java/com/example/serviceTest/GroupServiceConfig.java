package com.example.serviceTest;


import com.example.dao.GroupDao;
import com.example.service.AutoRun;
import com.example.service.GroupService;
import java.util.Random;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
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
  @MockBean
  AutoRun autoRun;
}
