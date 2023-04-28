package com.example.dao;

import com.example.model.Group;
import jakarta.persistence.EntityManager;
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
    for (Group group : groupList) {
      logger.trace("entityManager.persist({})", group);
      entityManager.persist(group);
    }
  }

  public List<Group> getAll() {
    return entityManager.createQuery("from Group").getResultList();
  }

  public List<Group> getGroupsByStudentCount(int number) {

    String query = "from Group group  join fetch group.students as stud "
        + "where size(stud) <= :number order by size(stud)";

    return entityManager.createQuery(query, Group.class)
        .setParameter("number", number).getResultList();
  }

  public void clearAll() {
    String query = "TRUNCATE  groups RESTART IDENTITY CASCADE;";
    entityManager.createNativeQuery(query).executeUpdate();
  }
}

