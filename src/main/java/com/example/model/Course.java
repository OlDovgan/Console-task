package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
public class Course {

  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "sequence_course", sequenceName = "courses_seq", allocationSize = 10)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_course")
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,
      CascadeType.REFRESH}, fetch = FetchType.EAGER)
  @JoinTable(name = "students_courses",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "student_id"))

  private List<Student> studentList;

  public Course() {
  }

  public Course(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void addStudentToCourse(Student student) {
    if (studentList == null) {
      studentList = new ArrayList<>();
    }
    studentList.add(student);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }

    Course course = (Course) obj;
    if (!this.name.equals(course.getName())) {
      return false;
    }
    if (!this.description.equals(course.getDescription())) {
      return false;
    }

    return this.id == course.getId();
  }

  @Override
  public int hashCode() {
    int result = name == null ? 0 : name.hashCode();
    result = 31 * result + id;
    result = 31 * result + description == null ? 0 : description.hashCode();
    return result;
  }
}
