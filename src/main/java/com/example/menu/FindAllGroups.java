package com.example.menu;

import com.example.Settings;
import com.example.dao.GroupDao;
import com.example.menu.MainMenu.SecondMenu;
import com.example.model.Group;
import java.sql.SQLException;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@SecondMenu
@Order(1)
public class FindAllGroups implements Menu {

  private final Settings settings;
  private final GroupDao groupDao;

  @Autowired
  public FindAllGroups(Settings settings, GroupDao groupDao) {
    this.settings = settings;
    this.groupDao = groupDao;
  }

  @Override
  public String getItemName() {
    return "Find all groups with less or equals student count";
  }

  @Override
  public void executeMenu() throws SQLException {
    int amount = settings.readInt("Please enter the number of students in the group ");
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    stringJoiner.add("Num| Groups");
    stringJoiner.add("---+-------");
    stringJoiner.add(findGroups(amount));
    System.out.println(stringJoiner);
    settings.endExecution();
  }

  public String findGroups(int number) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Group group : groupDao.getGroupsByStudentCount(number)) {
      stringJoiner.add(group.getNumberStudent() + " | " + group.getGroupName());
    }
    return stringJoiner.toString();
  }
}
