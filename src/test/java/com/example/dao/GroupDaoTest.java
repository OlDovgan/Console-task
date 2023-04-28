package com.example.dao;

import com.example.TestConfig;
import com.example.model.Group;
import com.example.model.Student;
import com.example.extra.TestUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")
class GroupDaoTest {

  @Autowired
  TestUtils utils;
  @Autowired
  GroupDao groupDao;
  @Autowired
  StudentDao studentDao;
  List<Group> groupList = new ArrayList<>();

  @BeforeEach
  void start() {
    utils.clearData();
  }

  @Test
  void add_ShouldAddGroupToDB() {
    boolean exist = false;
    Group group = new Group("FR-t6");
    if (!utils.isExistGroup(group.getName())) {
      groupDao.add(group);
      exist = utils.isExistGroup(group.getName());
    }
    Assertions.assertTrue(exist);
  }

  @Test
  void add_ShouldAddGroupListToDB() {
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    firstGroup.setStudents(new ArrayList<>());
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    secondGroup.setStudents(new ArrayList<>());
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, utils.getGroupList());

  }

  @Test
  void getAll_ShouldFindAllGroupsFromDbWhereTableStudentsIsEmpty() {
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, groupDao.getAll());
  }

  @Test
  void getAll_ShouldFindAllGroupsFromDbWhereTableStudentsHasStudentWithOutGroup() {
    Student student = new Student();
    student.setFirstName("Max");
    student.setLastName("Smith");
    studentDao.add(student);
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, groupDao.getAll());
  }

  @Test
  void getGroupsByStudentCount_ShouldFindGroupsFromDbWhereNumberStudentLessEqualThan() {

    int num = 3;
    Group firstGroup = new Group();
    firstGroup.setName("cP-50");
    Group secondGroup = new Group();
    secondGroup.setName("Jp-04");
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Student student = new Student();
    student.setGroupId(1);
    student.setFirstName("Max");
    student.setLastName("Smith");
    studentDao.add(student);
    Student studentSecond = new Student();
    studentSecond.setFirstName("Max1");
    studentSecond.setLastName("Smith1");
    studentSecond.setGroupId(2);
    studentDao.add(studentSecond);
    Assertions.assertEquals(groupList, groupDao.getGroupsByStudentCount(num));

  }
}
