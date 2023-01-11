package com.example;

import com.example.dao.CourseDao;
import com.example.dao.StudentDao;
import com.example.model.Course;
import com.example.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Result {

  @Autowired
  private StudentDao studentDao;
  @Autowired
  private CourseDao courseDao;
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();

  public String studentsWithCourse(String courseName) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String groupName;
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (courseName.length() + 2) + "s| %s";
    for (Student stud : studentDao.getWithCourse()) {
      for (Course course : stud.getCourseList()) {
        if (course.getCourseName().equals(courseName)) {
          if (stud.getGroupName() == null) {
            groupName = "without a group";
          } else {
            groupName = stud.getGroupName();
          }
          stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
              stud.getLastName(), course.getCourseName(), groupName));
        }
      }
    }
    return stringJoiner.toString();
  }

  public String studentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : studentDao.getWithOutCourse(course)) {
      stringJoiner.add(String.format(format, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String getStudentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : studentDao.getWithCourse()) {
      stringJoiner.add(String.format(FORMAT, stud.getStudentId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentInfoPrint() {
    StringJoiner joiner = header();
    for (Student st : studentDao.getAll()) {
      joiner.add(String.format(FORMAT, st.getStudentId(),
          st.getFirstName(), st.getLastName()));
    }
    return joiner.toString();
  }

  private StringJoiner header() {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    joiner.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
    joiner.add(String.format(FORMAT, "-", "-", " -")
        .replace('|', '+')
        .replace(' ', '-'));
    return joiner;
  }

  public String getStudentsCourse(int studentId) {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    System.out.println("The action completed successfully" + separator);

    for (Student st : studentDao.getAll()) {
      if (st.getCourseList() != null && st.getStudentId() == studentId) {
        for (Course course : st.getCourseList()) {
          joiner.add(st.getStudentId() + " | " + st.getFirstName() + "  "
              + st.getLastName() + " |  " + course.getCourseId() + " |  "
              + course.getCourseName());
        }
      }
    }
    return joiner.toString();
  }

  public String coursesInfo() {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    for (Course course : courseDao.getAll()) {
      joiner.add(course.getCourseId() + ". " + course.getCourseName());
      courses.add(course.getCourseName());
    }
    return joiner.toString();
  }
}
