package com.example.dao;

import com.example.model.Course;
import com.example.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseDao {

  private final CourseRepository repository;

  public CourseDao(CourseRepository repository) {
    this.repository = repository;
  }

  public void add(Course course) {
    repository.save(course);
  }

  public void add(List<Course> courseList) {
    repository.saveAll(courseList);
  }

  public List<Course> getAll() {
    return repository.findAll();
  }

  public void clearAll() {
  repository.truncateTable();
  }
}

