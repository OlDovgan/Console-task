package com.example.repository;

import com.example.model.Student;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  @Modifying
  @Transactional
  @Query(value = "TRUNCATE students, students_courses RESTART IDENTITY;", nativeQuery = true)
  void truncateTable();

  @Query(value = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
      + " WHERE students_courses.student_id=students.id "
      + " AND students_courses.course_id = ? );", nativeQuery = true)
  List<Student> findWithOutCourse(int courseId);

  @Query("SELECT s FROM Student s join  s.courses as c where size(c)>=1 ")
  List<Student> findWithCourse();
}
