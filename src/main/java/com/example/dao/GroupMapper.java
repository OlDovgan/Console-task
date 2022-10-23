package com.example.dao;

import com.example.model.Group;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GroupMapper implements RowMapper<Group> {

  @Override
  public Group mapRow(ResultSet result, int rowNum) throws SQLException {
    Group group = new Group();
    group.setGroupId(result.getInt(1));
    group.setGroupName(result.getString(2));
    return group;
  }
}
