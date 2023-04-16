package com.example.dao;

import com.example.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseDao {

  private final Logger logger
      = LoggerFactory.getLogger(CourseDao.class);

  @Autowired
  private EntityManager entityManager;

  public void add(Course course) {
    entityManager.persist(course);
  }

  public void add(List<Course> courseList) {
    for (Course course : courseList) {
      logger.trace("entityManager.persist({})", course);
      entityManager.persist(course);
    }
  }

  public List<Course> getAll() {
    String query = "SELECT i FROM Course i";
    TypedQuery<Course> typedQuery = entityManager.createQuery(query, Course.class);
    return typedQuery.getResultList();
  }
  public void clearAll() {
     String query = "TRUNCATE  courses RESTART IDENTITY CASCADE;";
      entityManager.createNativeQuery(query).executeUpdate();
  }
}

