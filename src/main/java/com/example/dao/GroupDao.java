package com.example.dao;

import com.example.mapper.GroupMapper;
import com.example.model.Group;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GroupDao {
  private static final Logger logger
      = LoggerFactory.getLogger(GroupDao.class.getName());

  private final JdbcTemplate jdbcTemplate;
  private final GroupMapper groupMapper;

  @Autowired
  public GroupDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.groupMapper = groupMapper;

  }

  public void add(Group group) {
    logger.trace("Start add(Group group)");
    jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?);", group.getName());
    logger.trace("Finish add(Group group)");
  }

  public void add(List<Group> groupList) {
    logger.trace("Start add(List<Group> groupList)");
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
    logger.trace("Finish add(List<Group> groupList)");

  }

  public List<Group> getAll() {
    logger.trace("Start getAll");

    String sql = "SELECT group_id,  group_name, number_student "
        + "FROM(SELECT groups.group_name AS group_name, groups.group_id, "
        + "COUNT (students.group_id) AS number_student "
        + "FROM groups LEFT JOIN students ON students.group_id = groups.group_id "
        + "GROUP BY groups.group_id, groups.group_name ) AS stud ORDER BY group_id;";

    return jdbcTemplate.query(sql, groupMapper);
  }

  public List<Group> getGroupsByStudentCount(int number) {
    logger.trace("Start getGroupsByStudentCount(int number)");
    String sql = "SELECT group_id,  group_name, number_student "
        + "FROM(SELECT groups.group_name AS group_name, groups.group_id, "
        + "COUNT (students.group_id) AS number_student "
        + "FROM groups LEFT JOIN students ON students.group_id = groups.group_id "
        + "GROUP BY groups.group_id, groups.group_name ) AS stud WHERE number_student <= ? "
        + "ORDER BY number_student;";
    return jdbcTemplate.query(sql, groupMapper, number);
  }
  public void clearAll() {
    logger.trace("Start clearAll() - TRUNCATE  groups RESTART IDENTITY;");
    jdbcTemplate.update("TRUNCATE  groups RESTART IDENTITY;");
    logger.trace("Finish clearAll() - TRUNCATE  groups RESTART IDENTITY;");

  }
}

