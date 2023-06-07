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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "sequence_student", sequenceName = "students_seq", allocationSize = 200)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_student")
  private Integer id;
  @Column(name = "group_id")
  private Integer groupId;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinTable(name = "students_courses",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))

  private List<Course> courses;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.REFRESH})
  @JoinColumn(name = "group_id", insertable = false, updatable = false)
  private Group group;

  public void addCourseToStudent(Course course) {
    if (courses == null) {
      courses = new ArrayList<>();
    }
    courses.add(course);
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }

    Student student = (Student) obj;
    if (!Objects.equals(this.groupId, student.getGroupId())) {
      return false;
    }
    if (!this.firstName.equals(student.getFirstName())) {
      return false;
    }
    if (!this.lastName.equals(student.getLastName())) {
      return false;
    }

    return Objects.equals(this.id, student.getId());
  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + id;
    if (groupId == null) {
      groupId = 0;
    }
    result = 31 * result + groupId;
    result = 31 * result + lastName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", groupId=" + groupId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", courses=" + courses +
        '}';
  }
}
