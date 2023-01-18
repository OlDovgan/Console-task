package com.example.dao.test;

import com.example.Data;
import com.example.dao.GroupDao;
import com.example.extra.TestUtils;
import com.example.model.Group;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentDaoTestConfig.class)
@ActiveProfiles("Test")
class GroupDaoTest {

  @Autowired
  TestUtils utils;
  @Autowired
  GroupDao groupDao;

  @Autowired
  Data data;
  List<Group> groupList = new ArrayList<>();

  @BeforeEach
  void start() {
    utils.clearData();
  }

  @Test
  void add_ShouldAddGroupToDB() {
    Group group = new Group("FR-t6");
    groupDao.add(group);
    Assertions.assertTrue(utils.isExistGroup(group.getName()));
  }

  @Test
  void add_ShouldAddGroupListToDB() {
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    firstGroup.setId(1);
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    secondGroup.setId(2);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, utils.getGroupList());

  }

  @Test
  void getGroupsByStudentCount_ShouldFindGroupsFromDbWhereNumberStudentLessEqualThan()
      throws IOException, URISyntaxException {
    data.addAllData();
    int num=5;
    Group firstGroup = new Group();
    firstGroup.setName("cP-50");
    firstGroup.setNumber_student(2);
    firstGroup.setId(4);
    Group secondGroup = new Group();
    secondGroup.setName("Jp-04");
    secondGroup.setNumber_student(3);
    secondGroup.setId(3);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);

    Assertions.assertEquals(groupList, groupDao.getGroupsByStudentCount(num));
  }
}
