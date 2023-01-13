package com.example.dao;

import com.example.mapper.GroupMapper;
import com.example.model.Group;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDao {

  private  JdbcTemplate jdbcTemplate;
  private GroupMapper mapper;

  @Autowired
  public GroupDao(JdbcTemplate jdbcTemplate, GroupMapper mapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = mapper;
  }
  public void add(Group group) {
    jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?);", group.getName());
  }

  public void add(List<Group> groupList) {
    jdbcTemplate.batchUpdate("INSERT INTO groups (group_name) VALUES (?);",
        new BatchPreparedStatementSetter() {
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, groupList.get(i).getName());
          }

          public int getBatchSize() {
            return groupList.size();
          }
        }
    );
  }

  public List<Group> getAll() {

    return jdbcTemplate.query("SELECT * FROM groups;", mapper);
  }

  public List<Group> getGroupsByStudentCount(int number) {
    String sql = "SELECT  number_student, stud.group_name " +
        "FROM(SELECT groups.group_name AS group_name,students.group_id,"
        + " COUNT (students.group_id) AS number_student "
        + "FROM students "
        + "JOIN groups "
        + "ON students.group_id= groups.group_id "
        + "GROUP BY students.group_id, groups.group_name ) AS stud "
        + "WHERE number_student <= ? "
        + "ORDER BY number_student;";

    return jdbcTemplate.query(sql, mapper, number);
  }
}
