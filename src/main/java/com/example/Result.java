package com.example;

import com.example.model.Course;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.StudentService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Result {

  @Autowired
  private StudentService studentList;
  @Autowired
  private CourseService courseService;
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";




  public String studentsWithCourse(String course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String groupName;
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (course.length() + 2) + "s| %s";
    for (Student stud : studentList.getWithCourse(course)) {
      if (stud.getGroup() == null) {
        groupName = "without a group";
      } else {
        groupName = stud.getGroup().getName();
      }
      stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
          stud.getLastName(), course, groupName));
    }
    return stringJoiner.toString();
  }

  public String studentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
    for (Student stud : studentList.getWithOutCourse(course)) {
      stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String getStudentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    for (Student stud : studentList.getWithCourse()) {
      stringJoiner.add(String.format(FORMAT, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentInfoPrint(List<Student> studentList) {
    StringJoiner joiner = header();
    for (Student st : studentList) {
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

  @Transactional
  public String getStudentsCourse(int studentId) {
    Student student = studentList.getStudentById(studentId);

    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);

    if (student.getCourses() != null) {
      for (Course course : student.getCourses()) {
        joiner.add(student.getId() + " | " + student.getFirstName() + "  "
            + student.getLastName() + " |  " + course.getId() + " |  "
            + course.getName());
      }
    }
    return joiner.toString();
  }

  public String coursesInfo(List<Course> courseList) {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    for (Course course : courseList) {

      joiner.add(course.getId() + ". " + course.getName());
    }
    return joiner.toString();
  }
  public List<String> getCourseNames(){
    List<String> courses = new ArrayList<>();
    for (Course course : courseService.getAll()) {
       courses.add(course.getName());
    }
    return  courses;
  }
}
