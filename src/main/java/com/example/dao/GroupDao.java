package com.example.dao;

import com.example.model.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GroupDao {

  private final Logger logger
      = LoggerFactory.getLogger(GroupDao.class);

  @Autowired
  private EntityManager entityManager;

  public void add(Group group) {
    entityManager.persist(group);
  }

  public void add(List<Group> groupList) {
    logger.debug("groupList ={}", groupList);
    for (Group group : groupList) {
      logger.trace("entityManager.persist({})", group);
      entityManager.persist(group);
    }
  }

  public List<Group> getAll() {

    String query = "SELECT i FROM Group i";
    TypedQuery<Group> typedQuery = entityManager.createQuery(query, Group.class);
    return typedQuery.getResultList();
  }

  public List<Group> getGroupsByStudentCount(int number) {
    String query = "SELECT i FROM Group i where ";
    TypedQuery<Group> typedQuery = entityManager.createQuery(query, Group.class);
    List<Group> groupList = new ArrayList<>();
    for (Group group : typedQuery.getResultList()) {
      if (group.getStudents().size() <= number) {
        groupList.add(group);
      }
    }
      return groupList;
  }

  public void clearAll() {
    String query = "TRUNCATE  groups RESTART IDENTITY CASCADE;";
    entityManager.createNativeQuery(query).executeUpdate();
  }
}

