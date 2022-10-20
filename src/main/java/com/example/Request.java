package com.example;

import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.dao.StudentsCourseDao;
import com.example.entity.Course;
import com.example.entity.Group;
import com.example.entity.Student;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
public class Request {

  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();
  private final JDBC jdbc;
  private final StudentDao studentDao;


  public Request(JDBC jdbc, StudentDao studentDao) {

    this.jdbc = jdbc;
    this.studentDao = studentDao;
  }

  public void deleteStudent(int id) throws SQLException {
     studentDao.delete(jdbc, id);
  }

  public void deleteStudentFromCourse(int studentId, int courseId) throws SQLException {
    StudentsCourseDao studentsCourseDao = new StudentsCourseDao();
    studentsCourseDao.delete(jdbc, studentId, courseId);
  }

  public String findStudentsWithCourse(String courseName)
      throws SQLException {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (courseName.length() + 2) + "s| %s";
    for (Student stud : studentDao.getStudentWithCourse(jdbc, courseName)) {
      stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName(), stud.getCourseName(), stud.getGroupName()));
    }
    return stringJoiner.toString();
  }


  public String findStudentsWithOutCourse(int course)
      throws SQLException {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : studentDao.getStudentsWithOutCourse(jdbc, course)) {
      stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentsWhereCourseIsExists() throws SQLException {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : studentDao.getStudentsWhereCourseIsExists(jdbc)) {
      stringJoiner.add(String.format(FORMAT, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public boolean studentsWhereCourseIsNotExists(int studId, int courseId)
      throws SQLException {
    return studentDao.getStudentsWhereCourseIsNotExists(jdbc, studId, courseId);
  }

  public String studentInfoPrint()
      throws SQLException {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    joiner.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
    joiner.add(String.format(FORMAT, "-", "-", " -")
        .replace('|', '+')
        .replace(' ', '-'));

    for (Student st : studentDao.getStudent(jdbc)) {
      joiner.add(String.format(FORMAT, st.getStudentId(),
          st.getFirstName(), st.getLastName()));
    }
    return joiner.toString();
  }

  public String coursesInfoPrint() throws SQLException {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    for (Course course : new CourseDao().getCourse(jdbc)) {
      joiner.add(course.getCourseId() + ". " + course.getCourseName());
      courses.add(course.getCourseName());
    }
    return joiner.toString();
  }

  public void addStudent(String firstName, String lastName)
      throws SQLException {
    Student student = new Student();
    student.setFirstName(firstName);
    student.setLastName(lastName);
    new StudentDao().add(jdbc, student);
  }

  public void addStudentToCourse(int student, int course) throws SQLException {
    new StudentsCourseDao().add(jdbc, student, course);
  }

  public String findCoursesOfStudent(int studentId) throws SQLException {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    System.out.println("The entry added successfully" + separator);
    for (Student st : studentDao.getCoursesOfStudent(jdbc, studentId)) {
      joiner.add(st.getStudentId() + " | " + st.getFirstName() + "  "
          + st.getLastName() + " |  " + st.getCourseId() + " |  " + st.getCourseName());
    }
    return joiner.toString();
  }

  public String findGroups(int number)
      throws SQLException {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Group group : new GroupDao().getCoursesOfStudent(jdbc, number)) {
      stringJoiner.add(group.getNumberStudent() + " | " + group.getGroupName());
    }
    return stringJoiner.toString();
  }
}
