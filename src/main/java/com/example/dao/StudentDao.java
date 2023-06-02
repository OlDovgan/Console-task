package com.example.dao;


import com.example.model.Course;
import com.example.model.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDao {

  private final StudentRepository repository;

  @Autowired
  private CourseRepository courseRepository;

  public StudentDao(StudentRepository repository) {
    this.repository = repository;
  }


  public void add(Student student) {
    repository.save(student);
  }

  @Transactional
  public void add(List<Student> studentList) {
    repository.saveAllAndFlush(studentList);
  }

  public void addStudentsCourse(int studentId, int courseId) throws Exception {

    Optional<Student> studentOptional = repository.findById(studentId);
    Optional<Course> courseOptional = courseRepository.findById(courseId);
    if (studentOptional.isEmpty()) {
      throw new IllegalStateException("Processing fail. Got a null response from students,"
          + "method - addStudentsCourse");
    }
    if (courseOptional.isEmpty()) {
      throw new IllegalStateException("Processing fail. Got a null response from courses,"
          + "method - addStudentsCourse");
    }
    Student student = studentOptional.get();
    Course course = courseOptional.get();
    student.addCourseToStudent(course);
    repository.save(student);
  }

  public List<Student> getAll() {
    return repository.findAll();
  }

  public Student getStudentById(int id) throws Exception {
    Optional<Student> studentOptional = repository.findById(id);
    if (studentOptional.isEmpty()) {
      throw new IllegalStateException("Processing fail. Got a null response from students,"
          + " method -getStudentById");
    }
    return studentOptional.get();
  }


  public List<Student> getWithOutCourse(int courseId) {

    return repository.findWithOutCourse(courseId);
  }

  public List<Student> getWithCourse() {
    return repository.findWithCourse();
  }

  public List<Student> getWithCourse(String courseName) {
    List<Student> studentList = new ArrayList<>();
    for (Student stud : getWithCourse()) {
      for (Course course : stud.getCourses()) {
        if (course.getName().equals(courseName)) {
          studentList.add(stud);
        }
      }
    }
    return studentList;
  }

  public void deleteById(int id) {
    repository.deleteById(id);
  }

  public void deleteFromCourse(int studentId, int courseId) throws Exception {
    Optional<Student> studentOptional = repository.findById(studentId);
    if (studentOptional.isEmpty()) {
      throw new IllegalStateException("Processing fail. Got a null response from students, "
          + "method -deleteFromCourse");
    }
    Student student = studentOptional.get();
    List<Course> courses = student.getCourses();
    courses.removeIf(c -> c.getId() == courseId);
    student.setCourses(courses);
    repository.save(student);
  }

  public void clearAll() {
    repository.truncateTable();
  }
}
