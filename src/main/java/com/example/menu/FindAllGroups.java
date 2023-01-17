package com.example.menu;

import com.example.Utility;
import com.example.dao.GroupDao;
import com.example.model.Group;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(1)
public class FindAllGroups implements Menu {

  private final Utility service;
  private final GroupDao groupDao;

  @Autowired
  public FindAllGroups(Utility service, GroupDao groupDao) {
    this.service = service;
    this.groupDao = groupDao;
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
    for (Group group : groupDao.getGroupsByStudentCount(number)) {
      stringJoiner.add(group.getNumberStudent() + " | " + group.getName()+" | " );
    }
    return stringJoiner.toString();
  }
}
