package com.example.dao.test;

import com.example.Data;
import com.example.dao.GroupDao;
import com.example.extra.TestUtils;
import com.example.model.Group;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes =GroupTestConfig.class)
@ActiveProfiles("Test")
public class GroupDaoTest {
  @Autowired
  TestUtils utils;
  @Autowired
  GroupDao groupDao;
  @Autowired
  Data data;
  @Value("${table}")
  private static String table;
  @Container
  private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>()
      .withInitScript(table);
  List<Group> groupList = new ArrayList<>();

  @Test
  void add_ShouldAddGroupToDB() {
    utils.clearData();
    Group group = new Group(10,"FR-t6");
    groupDao.add(group);
    Assertions.assertTrue(utils.isExistGroup(group.getGroupName()));
  }

  @Test
  void add_ShouldAddGroupListToDB() {
    utils.clearData();
    Group firstGroup = new Group();
    firstGroup.setGroupName("FR-05");
    firstGroup.setGroupId(1);
    Group secondGroup = new Group();
    secondGroup.setGroupName("sP-11");
    secondGroup.setGroupId(2);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList,utils.getGroupList());

  }

  @Test
  void getAll_ShouldFindAllGroupsFromDB() {
    Assertions.assertEquals(groupList, groupDao.getAll());
  }
}
