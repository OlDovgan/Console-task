package com.example.dao;

import com.example.model.Group;
import com.example.repository.GroupRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public class GroupDao {

  private final GroupRepository repository;

  public GroupDao(GroupRepository repository) {
    this.repository = repository;
  }

  public void add(Group group) {
    repository.save(group);
  }

  public void add(List<Group> groupList) {
    repository.saveAll(groupList);
  }

  public List<Group> getAll() {
    return repository.findAll();
  }

  public List<Group> getGroupsByStudentCount(int number) {
    return repository.getGroupsByStudentCount(number);
  }

  public void clearAll() {
    repository.truncateTable();
  }
}

