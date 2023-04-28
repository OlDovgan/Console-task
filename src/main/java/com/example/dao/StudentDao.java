package com.example.dao;


import com.example.model.Course;
import com.example.model.Student;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDao {

  private final Logger logger
      = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private EntityManager entityManager;

  public void add(Student student) {
    entityManager.merge(student);
  }

  public void add(List<Student> studentList) {

    for (Student student : studentList) {
      entityManager.merge(student);
    }
  }

  public void addStudentsCourse(int studentId, int courseId) {
    Student student = entityManager.find(Student.class, studentId);
    Course course = entityManager.find(Course.class, courseId);
    student.addCourseToStudent(course);
    entityManager.persist(student);
  }

  public List<Student> getAll() {
    return entityManager.createQuery("from Student").getResultList();
  }

  public Student getStudentById(int id) {
    return entityManager.find(Student.class, id);
  }


  public List<Student> getWithOutCourse(int courseId) {

    String sql = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.id "
        + " AND students_courses.course_id = ? );";
    List<Student> studentList = entityManager.createNativeQuery(sql, Student.class)
        .setParameter(1, courseId)
        .getResultList();
    logger.info("Student  {}, size {}", studentList, studentList.size());
    return studentList;
  }

  public List<Student> getWithCourse() {
    String query = "from Student stud join fetch stud.courses as c"
        + " where size(c)>=1";
    return entityManager.createQuery(query, Student.class).getResultList();
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
    entityManager.createQuery("delete from Student where id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }


  public void deleteFromCourse(int studentId, int courseId) {
    Student student = entityManager.find(Student.class, studentId);
    List<Course> courseNew = new ArrayList<>();
    for (Course course : student.getCourses()) {
      if (course.getId() != courseId) {
        courseNew.add(course);
      }
    }
    student.setCourses(courseNew);
    entityManager.merge(student);
  }

  public void clearAll() {
    String query = "TRUNCATE  students  RESTART IDENTITY CASCADE;";
    entityManager.createNativeQuery(query).executeUpdate();
    logger.debug("End entityManager.createNativeQuery({})", query);
  }
}
