package com.example.service.test;

import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.TestConfig;
import com.example.extra.TestUtils;
import com.example.model.Group;
import com.example.model.Student;
import com.example.service.GroupService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext
@ActiveProfiles("Test")
class GroupServiceTest {

  @Value("${groups}")
  private int groupsTest;
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  GroupDao groupDao;
  @Autowired
  StudentDao studentDao;
  @Autowired
  GroupService groupService;
  @Autowired
  TestUtils utils;
  @BeforeEach
  void start()  {
    utils.clearData();
  }
  @Test
  void createData_ShouldAddedSetQuantityGroupsToDb() {
    groupService.createData();
    int groups = 0;
    groups = jdbcTemplate.queryForObject("SELECT COUNT (group_id) FROM groups;",
        Integer.class);
    Assertions.assertEquals(groupsTest, groups);
  }

  @Test
  void getGroupsByStudentCount_ShouldFindGroupsFromDbWhereNumberStudentLessEqualThan() {
    List<Group> groupList = new ArrayList<>();
    int num = 3;
    Student student = new Student();
    student.setFirstName("Max");
    student.setLastName("Smith");
    studentDao.add(student);
    Student studentSecond = new Student();
    studentSecond.setFirstName("Max1");
    studentSecond.setLastName("Smith1");
    studentSecond.setGroupId(2);
    studentDao.add(studentSecond);

    Group firstGroup = new Group();
    firstGroup.setName("cP-50");
    firstGroup.setNumberStudent(0);
    firstGroup.setId(1);
    Group secondGroup = new Group();
    secondGroup.setName("Jp-04");
    secondGroup.setNumberStudent(1);
    secondGroup.setId(2);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);

    Assertions.assertEquals(groupList, groupService.getGroupsByStudentCount(num));
  }

  @Test
  void getAll_ShouldFindAllGroupsFromDb() {
    List<Group> groupList = new ArrayList<>();
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    firstGroup.setId(1);
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    secondGroup.setId(2);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, groupService.getAll());
  }
}
