package com.example.menu;

import com.example.Utility;
<<<<<<< HEAD
import com.example.dao.GroupDao;
import com.example.model.Group;
=======
import com.example.model.Group;
import com.example.service.GroupService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.sql.SQLException;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(1)
public class FindAllGroups implements Menu {

  private final Utility service;
<<<<<<< HEAD
  private final GroupDao groupDao;

  @Autowired
  public FindAllGroups(Utility service, GroupDao groupDao) {
    this.service = service;
    this.groupDao = groupDao;
=======
  private final GroupService groupService;

  @Autowired
  public FindAllGroups(Utility service, GroupService groupService) {
    this.service = service;
    this.groupService = groupService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  }

  @Override
  public String getItemName() {
    return "Find all groups with less or equals student count";
  }

  @Override
  public void executeMenu() throws SQLException {
    int studentNumber = service.readInt("Please enter the number of students in the group ");
    printGroupsInfo(studentNumber);
    service.endExecution();
  }

  private void printGroupsInfo(int studentNumber) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    stringJoiner.add("Num| Groups");
    stringJoiner.add("---+-------");
    stringJoiner.add(findGroups(studentNumber));
    System.out.println(stringJoiner);
  }

  public String findGroups(int number) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
<<<<<<< HEAD
    for (Group group : groupDao.getGroupsByStudentCount(number)) {
=======
    for (Group group : groupService.getGroupsByStudentCount(number)) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      stringJoiner.add(group.getNumberStudent() + " | " + group.getName());
    }
    return stringJoiner.toString();
  }
}
