package com.example;

import static com.example.spring_boot.Application.COURSE_DAO;
import static com.example.spring_boot.Application.STUDENT_DAO;

import com.example.model.Course;
import com.example.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;

@Component

public class Result {

  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();

  public String studentsWithCourse(String courseName) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String groupName;
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (courseName.length() + 2) + "s| %s";
    for (Student stud : STUDENT_DAO.getStudent()) {
      for (Course course : stud.getCourseList()) {
        if (course.getCourse_name().equals(courseName)) {
          if (stud.getGroup_name() == null) {
            groupName = "without a group";
          } else {
            groupName = stud.getGroup_name();
          }
          stringJoiner.add(String.format(format, stud.getStudent_id(), stud.getFirst_name(),
              stud.getLast_name(), course.getCourse_name(), groupName));
        }
      }
    }
    return stringJoiner.toString();
  }

  public String studentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : STUDENT_DAO.getStudentWithOutCourse(course)) {
      stringJoiner.add(String.format(format, stud.getStudent_id(), stud.getFirst_name(),
          stud.getLast_name()));
    }
    return stringJoiner.toString();
  }

  public String studentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : STUDENT_DAO.getStudentWithCourse()) {
      stringJoiner.add(String.format(FORMAT, stud.getStudent_id(), stud.getFirst_name(),
          stud.getLast_name()));
    }
    return stringJoiner.toString();
  }

  public String studentInfoPrint() {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    joiner.add(String.format(FORMAT, "ID", "Firs name", " Last name"));
    joiner.add(String.format(FORMAT, "-", "-", " -")
        .replace('|', '+')
        .replace(' ', '-'));

    for (Student st : STUDENT_DAO.getStudent()) {
      joiner.add(String.format(FORMAT, st.getStudent_id(),
          st.getFirst_name(), st.getLast_name()));
    }
    return joiner.toString();
  }

  public String studentsCourse(int studentId) {
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    System.out.println("The action completed successfully" + separator);
    for (Student st : STUDENT_DAO.getStudent()) {
      if (st.getCourseList() != null && st.getStudent_id() == studentId) {
        for (Course course : st.getCourseList()) {
          joiner.add(st.getStudent_id() + " | " + st.getFirst_name() + "  "
              + st.getLast_name() + " |  " + course.getCourse_id() + " |  "
              + course.getCourse_name());
        }
      }
    }
    return joiner.toString();
  }
    public String coursesInfoPrint() {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    for (Course course : COURSE_DAO.getAll()) {
      joiner.add(course.getCourse_id() + ". " + course.getCourse_name());
      courses.add(course.getCourse_name());
    }
    return joiner.toString();
  }

}
