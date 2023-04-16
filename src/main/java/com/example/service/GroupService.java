package com.example.service;

import com.example.dao.GroupDao;
import com.example.model.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private final Logger logger
      = LoggerFactory.getLogger(this.getClass());
  @Value("${groups}")
  private int groupsNumber;
  @Value("${students-total}")
  private int studentsTotalNumber;
  private Random random;
  public final GroupDao groupDao;

  @Autowired
  public GroupService(GroupDao groupDao, Random random) {
    this.groupDao = groupDao;
    this.random = random;
  }

  public void createData() {
    groupDao.clearAll();
    groupDao.add(createGroupsList());
  }

  private List<Group> createGroupsList() {
    logger.debug("Start createGroupsList()");
    List<Group> groupList = new ArrayList<>();
   // groupList.add(new Group());
    for (int i = 0;
        i < Math.min(groupsNumber, studentsTotalNumber); i++) {
      groupList.add(new Group(groupName(random, 2, 2)));
      logger.debug("groupsNumber = {}, studentsTotalNumber = {}",groupsNumber,studentsTotalNumber);
      logger.debug("groupList.add = {}", groupList);
    }
    return groupList;
  }

  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.random(characters, 0, 0, true, false, null, random) + "-"
        + RandomStringUtils.random(digitCount, 0, 0, false, true, null, random);
  }

  public List<Group> getGroupsByStudentCount(int number) {
    return groupDao.getGroupsByStudentCount(number);
  }

  public List<Group> getAll() {
    return groupDao.getAll();
  }

  public int getNumberStudent(Group group) {
    return group.getStudents().size();
  }
}
