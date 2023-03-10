package com.example.layer.service;

import com.example.layer.dao.GroupDao;
import com.example.model.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

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

    List<Group> groupList = new ArrayList<>();
    for (int i = 0;
        i < Math.min(groupsNumber, studentsTotalNumber); i++) {
      groupList.add(new Group(groupName(random, 2, 2)));
    }
    return groupList;
  }

  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.random(characters, 0, 0, true, false, null, random) + "-"
        + RandomStringUtils.random(digitCount, 0, 0, false, true, null, random);
  }
  public List<Group> getGroupsByStudentCount(int number){
   return groupDao.getGroupsByStudentCount(number);
  }
  public List<Group> getAll() {
    return groupDao.getAll();
  }
}
