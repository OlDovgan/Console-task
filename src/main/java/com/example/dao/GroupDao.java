package com.example.dao;

import com.example.model.Group;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GroupDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public GroupDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void add(Group group) {
    jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?);", group.getGroupName());
  }

  public void add(List<Group> groupList) {
    jdbcTemplate.batchUpdate("INSERT INTO groups (group_name) VALUES (?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, groupList.get(i).getGroupName());
          }
          public int getBatchSize() {
            return groupList.size();
          }
        }
    );
  }

  public List<Group> groupList() {

    return jdbcTemplate.query("SELECT * FROM groups;", new GroupMapper());
  }

  public List<Group> getCoursesOfStudent(int number) {
    List<Group> list = new ArrayList<>();
    String sql = "SELECT  myCount, stud.group " +
        "FROM(SELECT groups.group_name AS group,students.group_id,"
        + " COUNT (students.group_id) AS myCount "
        + "FROM students "
        + "JOIN groups "
        + "ON students.group_id= groups.group_id "
        + "GROUP BY students.group_id, groups.group_name ) AS stud "
        + "WHERE myCount <= ? "
        + "ORDER BY myCount;";
    return jdbcTemplate.query(sql, rs -> {

      while (rs.next()) {
        Group group = new Group();
        group.setGroupName(rs.getString(2));
        group.setNumberStudent(rs.getInt(1));
        list.add(group);
      }
      return list;
    }, number);
  }
}
