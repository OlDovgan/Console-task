package com.example;

import static com.example.spring_boot.Application.COURSE_DAO;
import static com.example.spring_boot.Application.DATA;
import static com.example.spring_boot.Application.GROUP_DAO;
import static com.example.spring_boot.Application.STUDENT_DAO;

import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:Task.properties")
public class Data {

  private final FileReader fileReader = new FileReader();
  private final Random random;

  @Value("${groups}")
  private String groupsNumProp;
  @Value("${courses}")
  private String courses;
  @Value("${students-total}")
  public String studTotal;
  @Value("${first-name-file}")
  private String firstNameFile;
  @Value("${last-name-file}")
  private String lastNameFile;
  @Value("${number-student-min}")
  private String numberStudentMin;
  @Value("${number-student-max}")
  private String numberStudentMax;
  @Value("${students-courses-min}")
  private String studentsCoursesMin;
  @Value("${students-courses-max}")
  private String studentsCoursesMax;

  @Autowired
  public Data(Random random) {
    this.random = random;
  }

  public final int randomInt(Random random, int origin, int bound) {
    if (origin >= bound) {
      throw new IllegalArgumentException();
    }
    return origin + random.nextInt(bound);
  }

  private String groupName(Random random, int characterСount, int digitCount) {
    return RandomStringUtils.
        random(characterСount, 0, 0, true, false, null, random)
        + "-" +
        RandomStringUtils.
            random(digitCount, 0, 0, false, true, null, random);
  }
  private void addGroups() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0; i < Math.min(Integer.valueOf(groupsNumProp),
        Integer.valueOf(studTotal)); i++) {
      groupList.add(new Group(0, groupName(random, 2, 2)));
    }
    GROUP_DAO.add(groupList);
  }

  private void addCourses()
      throws IOException, URISyntaxException {
    Map<String, String> courses = new LinkedHashMap<>();
    List<String> element = fileReader.readFile("Courses.txt");
    String join = String.join(" ", element);
    String[] split = join.split(";");
    for (String str : split) {
      String[] arr = (str.trim().split("-", 2));
      courses.put(arr[0].trim(), arr[1]);
    }
    List<Course> courseList = new ArrayList<>();
    for (Entry<String, String> entry : courses.entrySet()) {
      courseList.add(new Course(entry.getKey(), entry.getValue()));
    }
    COURSE_DAO.add(courseList);
  }

  private void addStudents()
      throws IOException, URISyntaxException {
    List<Student> studentList = new ArrayList<>();
    int studTotal = Integer.valueOf(DATA.studTotal);
    String[] firstNames = fileReader.readFile(firstNameFile)
        .toArray(new String[]{});
    String[] lastNames = fileReader.readFile(lastNameFile)
        .toArray(new String[]{});
    List<Integer> groupsId = new ArrayList<>();
    for (Group group : GROUP_DAO.getAll()) {
      groupsId.add(group.getGroup_id());
    }
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < groupsId.size(); i++) {
      set.add(groupsId.get((randomInt(random, 1, groupsId.size())) - 1));
    }
    int studentsWithGroup = 0;
    for (Integer d : set) {
      int number = randomInt(random,
          Integer.valueOf(numberStudentMin),
          Integer.valueOf(numberStudentMax));
      int i = 0;
      while (studentsWithGroup < studTotal && i <= number) {
        Student student = new Student();
        student.setGroup_id(d);
        student.setFirst_name(firstNames[randomInt(random, 0, firstNames.length - 1)]);
        student.setLast_name(lastNames[randomInt(random, 0, lastNames.length - 1)]);
        studentList.add(student);
        studentsWithGroup++;
        i++;
      }
    }
    for (int i = 0; i < studTotal - studentsWithGroup; i++) {
      Student student = new Student();
      student.setFirst_name(firstNames[randomInt(random, 0, firstNames.length - 1)]);
      student.setLast_name(lastNames[randomInt(random, 0, lastNames.length - 1)]);
      studentList.add(student);
    }
    STUDENT_DAO.add(studentList);
  }

  private void addStudentsCourses() {
    List<Course> courseList = COURSE_DAO.getAll();
    List<Student> studentList = STUDENT_DAO.getStudent();
    List<Student> studentListNew = new ArrayList<>();
    BitSet bitSet = new BitSet(Integer.valueOf(courses));
    for (Student student : studentList) {
      List<Course> courseListNew = new ArrayList<>();
      int k = randomInt(random,
          Integer.valueOf(studentsCoursesMin),
          Integer.valueOf(studentsCoursesMax));
      int i = 0;
      bitSet.clear();
      while (i < k) {
        int course = randomInt(random, 1,
            Integer.valueOf(courses));
        if (!bitSet.get(course)) {
          bitSet.set(course);
          i++;
          courseListNew.add(courseList.get(course - 1));
        }
      }
      student.setCourseList(courseListNew);
      studentListNew.add(student);
    }
    STUDENT_DAO.addStudentsCourse(studentListNew);
  }

  public void createAll()
      throws IOException, URISyntaxException {
    addGroups();
    addCourses();
    addStudents();
    addStudentsCourses();
  }
}
