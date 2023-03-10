package com.example;

import com.example.model.Course;
import com.example.model.Student;
import com.example.layer.service.CourseService;
import com.example.layer.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Result {

  @Autowired
  private StudentService studentService;
  @Autowired
  private CourseService courseService;
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();

  public String studentsWithCourse(String course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String groupName;
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (course.length() + 2) + "s| %s";
    for (Student stud : studentService.getWithCourse(course)) {
      if (stud.getGroupName() == null) {
        groupName = "without a group";
      } else {
        groupName = stud.getGroupName();
      }
      stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
          stud.getLastName(), course, groupName));
    }
    return stringJoiner.toString();
  }

  public String studentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : studentService.getWithOutCourse(course)) {
      stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String getStudentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : studentService.getWithCourse()) {
      stringJoiner.add(String.format(FORMAT, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentInfoPrint() {
    StringJoiner joiner = header();
    for (Student st : studentService.getAll()) {
      joiner.add(String.format(FORMAT, st.getId(),
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
    Student student = studentService.getStudentById(studentId);

    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);

    if (student.getCourse() != null) {
      for (Course course : student.getCourse()) {
        joiner.add(student.getId() + " | " + student.getFirstName() + "  "
            + student.getLastName() + " |  " + course.getId() + " |  "
            + course.getName());
      }
    }

    return joiner.toString();
  }

  public String coursesInfo() {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    for (Course course : courseService.getAll()) {
      joiner.add(course.getId() + ". " + course.getName());
      courses.add(course.getName());
    }
    return joiner.toString();
  }
}
