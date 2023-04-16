package com.example.dao;


import com.example.model.Course;
import com.example.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

  private int keyId(Student student) {
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//    String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES(?,?,?);";
//    jdbcTemplate.update(connection -> {
//      PreparedStatement ps = connection.prepareStatement(sql,
//          new String[]{"student_id"});
//      ps.setInt(1, student.getGroupId());
//      ps.setString(2, student.getFirstName());
//      ps.setString(3, student.getLastName());
//      return ps;
//    }, keyHolder);
//    return (int) keyHolder.getKey();
    return 5;
  }


  public void addStudentsCourse(int studentId, int courseId) {
    Student student = entityManager.find(Student.class, studentId);
    Course course = entityManager.find(Course.class, courseId);
    student.addCourseToStudent(course);
    entityManager.persist(student);
  }

//  public void addStudentsCourse(List<Student> studentsList){
//    for (Student student: studentsList) {
//      student.setCourseList();
//    }
//    List<Integer[]> list = new ArrayList<>();
//    for (Student student : studentsList) {
//      if (student.getCourse() != null) {
//        for (Course course : student.getCourse()) {
//          list.add(new Integer[]{student.getId(), course.getId()});
//        }
//      }
//    }
//
//    jdbcTemplate.batchUpdate(query,
//        new BatchPreparedStatementSetter() {
//          public void setValues(PreparedStatement ps, int i) throws SQLException {
//
//            ps.setInt(1, list.get(i)[0]);
//            ps.setInt(2, list.get(i)[1]);
//          }
//
//          public int getBatchSize() {
//            return list.size();
//          }
//        });
  // }

  public List<Student> getAll() {
  //  String query = "SELECT i FROM Student i";
    String query = "from Student";
    Query query1 = entityManager.createQuery(query);

  //  TypedQuery<Student> typedQuery = entityManager.createQuery(query, Student.class);

   // return typedQuery.getResultList();
    return query1.getResultList();
  }

  public Student getStudentById(int id) {
    return entityManager.find(Student.class, id);
  }

//  private List<Student> getAll(List<Student> studentList) {
//    List<Student> studentListNew = new ArrayList<>();
//    for (Student student : studentList) {
//      setStudentsGroupsName(student);
//      //     student.setCourse(getStudentsCourseByStudentId(student.getId()));
//      studentListNew.add(student);
//    }
//    return studentListNew;
//  }

  private List<Course> getStudentsCourseByStudentId(int studentId) {
    Student student = entityManager.find(Student.class, studentId);
    return student.getCourseList();
  }

//  private void setStudentsGroupsName(Student student) {
//
////    if (student.getGroupId() != 0) {
////      var groupName = jdbcTemplate.queryForObject(
////          "SELECT group_name FROM groups WHERE group_id = ? ;",
////          String.class, student.getGroupId());
////      student.setGroupName(groupName);
////    }
//  }

  public List<Student> getWithOutCourse(int courseId) {

//    List<Student> studentList;
//    String sql = "SELECT * FROM students WHERE NOT EXISTS (SELECT * FROM students_courses "
//        + " WHERE students_courses.student_id=students.student_id "
//        + " AND students_courses.course_id = ? );";
//    studentList = jdbcTemplate.query(sql, mapperStudent, courseId);
//    return getAll(studentList);
    return new ArrayList<>();
  }

  public List<Student> getWithCourse() {

    //    List<Student> studentList;
    String query = "SELECT * FROM students WHERE  EXISTS (SELECT * FROM students_courses "
        + " WHERE students_courses.student_id=students.id );";
  List<Student>  studentList = entityManager.createNativeQuery(query).getResultList();
    for (Student student:studentList) {
      logger.info("student id={}, group={}", student.getId(),student.getGroup());
    }

//    studentList = jdbcTemplate.query(sql, mapperStudent);
//    return getAll(studentList);
    return  studentList;
  }

  public List<Student> getWithCourse(String courseName) {
    List<Student> studentList = new ArrayList<>();
    for (Student stud : getWithCourse()) {
      for (Course course : stud.getCourseList()) {
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
  //  Student student = new Student();
 //   entityManager.merge(student);
//    Student student = entityManager.find(Student.class,id);
//    logger.info("Student = {}",student);
//    entityManager.remove(student);


  //  entityManager.flush();
  }


  public void deleteFromCourse(int studentId, int courseId) {
//    entityManager.createQuery("delete from Student where id = :id")
//        .setParameter("id", id)
//        .executeUpdate();

    Student student = entityManager.find(Student.class, studentId);
    List<Course> courseList = student.getCourseList();
    List<Course> courseNew = student.getCourseList();
    for (Course course:courseList) {
      if(course.getId()!=studentId) {
        courseNew.add(course);
      }
    }
    student.setCourseList(courseNew);
    entityManager.merge(student);
    //   jdbcTemplate.update("DELETE FROM students_courses WHERE student_id =? AND  course_id=? ;",
    //      studentId, courseId);
  }

  // @Query( value = "TRUNCATE   students_courses,students RESTART IDENTITY;", nativeQuery = true)
  public void clearAll() {
    String query = "TRUNCATE students RESTART IDENTITY CASCADE;";
    entityManager.createNativeQuery(query).executeUpdate();
    logger.info("End entityManager.createNativeQuery({})", query);
  }
}
