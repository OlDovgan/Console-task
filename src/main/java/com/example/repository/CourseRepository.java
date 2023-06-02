package com.example.repository;

import com.example.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course,Integer>  {
  @Modifying
  @Transactional
  @Query(value = "TRUNCATE  courses RESTART IDENTITY CASCADE;", nativeQuery = true)
  void truncateTable();

}
