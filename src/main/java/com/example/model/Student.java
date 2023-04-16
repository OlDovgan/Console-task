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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "group_id")
  private int groupId;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.REFRESH}, fetch = FetchType.EAGER)
  @JoinTable(name = "students_courses",
  joinColumns = @JoinColumn (name = "student_id"),
  inverseJoinColumns =@JoinColumn (name = "course_id") )
  private List<Course> courseList;


  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.REFRESH}, orphanRemoval = true)
  @JoinColumn(name = "group_id", insertable = false, updatable = false)
  private Group group;

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", groupId=" + groupId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }

  public Student() {
  }

  public Student(int groupId, String firstName, String lastName) {
    this.groupId = groupId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void addCourseToStudent(Course course) {
    if (courseList == null) {
      courseList = new ArrayList<>();
    }
    courseList.add(course);
  }
}
