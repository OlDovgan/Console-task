package com.example;

<<<<<<< HEAD
import com.example.dao.CourseDao;
import com.example.dao.StudentDao;
import com.example.model.Course;
import com.example.model.Student;
=======
import com.example.model.Course;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Result {

  @Autowired
<<<<<<< HEAD
  private StudentDao studentDao;
  @Autowired
  private CourseDao courseDao;
=======
  StudentService studentService;
  @Autowired
  CourseService courseService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  private static final String FORMAT = "%-" + 4 + "s| %-" + 12 + "s %-" + 12 + "s";
  public final List<String> courses = new ArrayList<>();

  public String studentsWithCourse(String courseName) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String groupName;
    String format =
        "%-3d| %-" + 12 + "s| %-" + 12 + "s| %-" + (courseName.length() + 2) + "s| %s";
<<<<<<< HEAD
    for (Student stud : studentDao.getWithCourse()) {
=======
    for (Student stud : studentService.getWithCourse()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      for (Course course : stud.getCourse()) {
        if (course.getName().equals(courseName)) {
          if (stud.getGroupName() == null) {
            groupName = "without a group";
          } else {
            groupName = stud.getGroupName();
          }
          stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
              stud.getLastName(), course.getName(), groupName));
        }
      }
    }
    return stringJoiner.toString();
  }

  public String studentsWithOutCourse(int course) {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
    String format = "%-" + 3 + "s| %-" + 12 + "s| %-" + 12 + "s";
<<<<<<< HEAD
    for (Student stud : studentDao.getWithOutCourse(course)) {
=======
    for (Student stud : studentService.getWithOutCourse(course)) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      stringJoiner.add(String.format(format, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String getStudentsWhereCourseIsExists() {
    StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
<<<<<<< HEAD
    for (Student stud : studentDao.getWithCourse()) {
=======
    for (Student stud : studentService.getWithCourse()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      stringJoiner.add(String.format(FORMAT, stud.getId(), stud.getFirstName(),
          stud.getLastName()));
    }
    return stringJoiner.toString();
  }

  public String studentInfoPrint() {
    StringJoiner joiner = header();
<<<<<<< HEAD
    for (Student st : studentDao.getAll()) {
=======
    for (Student st : studentService.getAll()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
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
    String separator = System.lineSeparator();
    StringJoiner joiner = new StringJoiner(separator);
    System.out.println("The action completed successfully" + separator);

<<<<<<< HEAD
    for (Student st : studentDao.getAll()) {
=======
    for (Student st : studentService.getAll()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      if (st.getCourse() != null && st.getId() == studentId) {
        for (Course course : st.getCourse()) {
          joiner.add(st.getId() + " | " + st.getFirstName() + "  "
              + st.getLastName() + " |  " + course.getId() + " |  "
              + course.getName());
        }
      }
    }
    return joiner.toString();
  }

  public String coursesInfo() {
    StringJoiner joiner = new StringJoiner(System.lineSeparator());
<<<<<<< HEAD
    for (Course course : courseDao.getAll()) {
=======
    for (Course course : courseService.getAll()) {
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
      joiner.add(course.getId() + ". " + course.getName());
      courses.add(course.getName());
    }
    return joiner.toString();
  }
}
