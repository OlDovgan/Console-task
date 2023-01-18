package com.example.mapper;

import com.example.model.Group;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper implements RowMapper<Group> {

  @Override
   public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
    Group group = new Group();
    group.setId(rs.getInt("group_id"));
    group.setName(rs.getString("group_name"));
    group.setNumberStudent(rs.getInt("number_student"));

    return  group;
  }
}
