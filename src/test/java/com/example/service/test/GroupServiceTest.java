package com.example.service.test;

import com.example.dao.test.StudentDaoTestConfig;
import com.example.extra.TestUtils;
import com.example.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")
//@TestInstance(Lifecycle.PER_CLASS)
 class GroupServiceTest {
  @Value("${groups}")
  private String groupsTest;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  GroupService groupService;
  @Autowired
  TestUtils utils;

  @Test
  void createData_ShouldAddedSetQuantityGroupsToDb() {
    utils.clearData();
    groupService.createData();
    int groups = 0;
    groups = jdbcTemplate.queryForObject("SELECT COUNT (group_id) FROM groups;",
        Integer.class);
    Assertions.assertEquals(Integer.valueOf(groupsTest), groups);
  }

}
