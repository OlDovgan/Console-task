package com.example.repository;

import com.example.model.Student;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  @Modifying
  @Transactional
  @Query(value = "TRUNCATE students, students_courses RESTART IDENTITY;", nativeQuery = true)
  void truncateTable();

  @Transactional
  @Query("SELECT s FROM Student s  WHERE  NOT EXISTS "
      + "(SELECT st FROM Student st join st.courses as c where st.id = s.id  AND c.id= :id)")
  List<Student> findWithOutCourse(@Param("id") int id);

  @Query("SELECT s FROM Student s join  fetch s.courses as c where size(c)>=1 ")
  List<Student> findWithCourse();
}
