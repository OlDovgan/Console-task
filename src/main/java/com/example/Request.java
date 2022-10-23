package com.example;

import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.dao.StudentsCourseDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("request")
public class Request {

  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();


  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public Request(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void deleteStudent(int id) {
    new StudentDao(jdbcTemplate).delete(id);
  }

  public void deleteStudentFromCourse(int studentId, int courseId) {
    StudentsCourseDao studentsCourseDao = new StudentsCourseDao(jdbcTemplate);
    studentsCourseDao.delete(studentId, courseId);
  }

  public String findStudentsWithCourse(String courseName) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (courseName.length() + 2) + "s| %s";
    for (Student stud : new StudentDao(jdbcTemplate).getStudentWithCourse(courseName)) {
      stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName(), stud.getCourseName(), stud.getGroupName()));
    }
    return stringJoiner.toString();
  }

  public String findStudentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : new StudentDao(jdbcTemplate).getStudentsWithOutCourse(course)) {
      stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : new StudentDao(jdbcTemplate).getStudentsWhereCourseIsExists()) {
      stringJoiner.add(String.format(FORMAT, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public boolean studentsWhereCourseIsNotExists(int studId, int courseId) {
    return new StudentDao(jdbcTemplate).getStudentsWhereCourseIsNotExists(studId, courseId);
  }

  public String studentInfoPrint() {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    joiner.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
    joiner.add(String.format(FORMAT, "-", "-", " -")
        .replace('|', '+')
        .replace(' ', '-'));

    for (Student st : new StudentDao(jdbcTemplate).getStudent()) {
      joiner.add(String.format(FORMAT, st.getStudentId(),
          st.getFirstName(), st.getLastName()));
    }
    return joiner.toString();
  }

  public String coursesInfoPrint() {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    for (Course course : new CourseDao(jdbcTemplate).getCourse()) {
      joiner.add(course.getCourseId() + ". " + course.getCourseName());
      courses.add(course.getCourseName());
    }
    return joiner.toString();
  }

  public void addStudent(String firstName, String lastName) {
    Student student = new Student();
    student.setFirstName(firstName);
    student.setLastName(lastName);
    new StudentDao(jdbcTemplate).add(student);
  }

  public void addStudentToCourse(int student, int course) {
    new StudentsCourseDao(jdbcTemplate).add(student, course);
  }

  public String findCoursesOfStudent(int studentId) {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    System.out.println("The entry added successfully" + separator);
    for (Student st : new StudentDao(jdbcTemplate).getCoursesOfStudent(studentId)) {
      joiner.add(st.getStudentId() + " | " + st.getFirstName() + "  "
          + st.getLastName() + " |  " + st.getCourseId() + " |  " + st.getCourseName());
    }
    return joiner.toString();
  }

  public String findGroups(int number) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Group group : new GroupDao(jdbcTemplate).getCoursesOfStudent(number)) {
      stringJoiner.add(group.getNumberStudent() + " | " + group.getGroupName());
    }
    return stringJoiner.toString();
  }
}
