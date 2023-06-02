package com.example.repository;

import com.example.model.Group;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group,Integer> {
  @Modifying
  @Transactional
  @Query(value = "TRUNCATE  groups RESTART IDENTITY CASCADE;", nativeQuery = true)
  void truncateTable();

  @Query("SELECT group from Group group  join fetch  group.students as stud "
      + "where size(stud) <= :student order by size(stud)")
  List<Group> getGroupsByStudentCount (@Param("student") int student);
}
