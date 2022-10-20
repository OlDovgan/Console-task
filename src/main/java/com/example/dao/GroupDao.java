package com.example.dao;

import com.example.JDBC;
import com.example.entity.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class GroupDao {

  public void add(JDBC jdbc, String groupName) throws SQLException {
    Group group = new Group(groupName);
    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO groups (group_name) VALUES (?);")) {
      preparedStatement.setString(1, group.getGroupName());
      preparedStatement.executeUpdate();
    }
  }
  public void add(JDBC jdbc, List<Group> groupList) throws SQLException {
    try (Connection connection = jdbc.getDbConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO groups (group_name) VALUES (?);")) {
      for (Group group: groupList ) {
        preparedStatement.setString(1, group.getGroupName());
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    }
  }
  public List<Group> groupList(JDBC jdbc) throws SQLException {
    List <Group> groupsList = new ArrayList<>();

    try (Statement statement = jdbc.getDbConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM groups;")) {
      while (result.next()) {
        Group group = new Group();
        group.setGroupId(result.getInt(1));
        group.setGroupName(result.getString(2));
        groupsList.add(group);
      }
    }
    return groupsList;
  }
  public List<Group> getCoursesOfStudent(JDBC jdbc, int number) throws SQLException {
    List<Group> list = new ArrayList<>();
    String sql ="SELECT  myCount, stud.group " +
        "FROM(SELECT groups.group_name AS group,students.group_id,"
        + " COUNT (students.group_id) AS myCount "
        + "FROM students "
        + "JOIN groups "
        + "ON students.group_id= groups.group_id "
        + "GROUP BY students.group_id, groups.group_name ) AS stud "
        + "WHERE myCount <= ? "
        + "ORDER BY myCount;";
    try (PreparedStatement preparedStatement = jdbc.getDbConnection().prepareStatement(sql)) {
      preparedStatement.setInt(1, number);
      ResultSet res = preparedStatement.executeQuery();
      while (res.next()) {
        Group group = new Group();
        group.setGroupName(res.getString(2));
        group.setNumberStudent(res.getInt(1));
        list.add(group);
      }
      return list;
    }
  }
}
