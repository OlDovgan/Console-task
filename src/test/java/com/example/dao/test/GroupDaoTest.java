package com.example.dao.test;

import com.example.Data;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.extra.TestUtils;
import com.example.model.Group;
import com.example.model.Student;
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
  StudentDao studentDao;
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
  void getAll_ShouldFindAllGroupsFromDbWhereTableStudentsIsEmpty() {
    Group firstGroup = new Group();
    firstGroup.setName("FR-05");
    firstGroup.setId(1);
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    secondGroup.setId(2);
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
    firstGroup.setId(1);
    Group secondGroup = new Group();
    secondGroup.setName("sP-11");
    secondGroup.setId(2);
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupDao.add(groupList);
    Assertions.assertEquals(groupList, groupDao.getAll());
  }

  @Test
  void getGroupsByStudentCount_ShouldFindGroupsFromDbWhereNumberStudentLessEqualThan() {

    int num=3;
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

    Assertions.assertEquals(groupList, groupDao.getGroupsByStudentCount(num));
  }
}
