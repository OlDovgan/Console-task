package com.example;

import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


@Service
@PropertySource("classpath:Task.properties")
public class Data {

  @Value("${groups}")
  private String groupsNumProp;
  @Value("${courses}")
  private String coursesProp;
  @Value("${students-total}")
  private String studentsTotal;
  @Value("${first-name-file}")
  private String firstNameFile;
  @Value("${last-name-file}")
  private String lastNameFile;
  @Value("${course-name-file}")
  private String coursesNameFile;
  @Value("${number-student-min}")
  private String numberStudentMin;
  @Value("${number-student-max}")
  private String numberStudentMax;
  @Value("${students-courses-min}")
  private String studentsCoursesMin;
  @Value("${students-courses-max}")
  private String studentsCoursesMax;
  private  Random random;
  @Autowired
  private  GroupDao groupDao;
  @Autowired
  private  CourseDao courseDao;
  @Autowired
  private  StudentDao studentDao;
  @Autowired
  private  FileReader fileReader;

  @Autowired
  public Data( Random random) {
    this.random = random;
  }
   public final int randomInt(Random random, int origin, int bound) {
    if (origin >= bound) {
      throw new IllegalArgumentException();
    }
    return origin + random.nextInt(bound);
  }


  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.
        random(characters, 0, 0, true, false, null, random)
        + "-" +
        RandomStringUtils.
            random(digitCount, 0, 0, false, true, null, random);
  }

  private void addGroups() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0; i < Math.min(Integer.valueOf(groupsNumProp),
        Integer.valueOf(studentsTotal)); i++) {
      groupList.add(new Group(0, groupName(random, 2, 2)));
    }
    groupDao.add(groupList);
  }

  private void addCourses()
      throws IOException, URISyntaxException {
    Map<String, String> coursesMap = new LinkedHashMap<>();
    List<String> element = fileReader.readFile(coursesNameFile);
    String join = String.join(" ", element);
    String[] split = join.split(";");
    for (String str : split) {
      String[] arr = (str.trim().split("-", 2));
      coursesMap.put(arr[0].trim(), arr[1]);
    }
    List<Course> courseList = new ArrayList<>();
    for (Entry<String, String> entry : coursesMap.entrySet()) {
      courseList.add(new Course(entry.getKey(), entry.getValue()));
    }
    courseDao.add(courseList);
  }

  private void addStudents()
      throws IOException, URISyntaxException {
    List<Student> studentList = new ArrayList<>();
    int studTotal = Integer.valueOf(studentsTotal);
    String[] firstNames = fileReader.readFile(firstNameFile)
        .toArray(new String[]{});
    String[] lastNames = fileReader.readFile(lastNameFile)
        .toArray(new String[]{});
    List<Integer> groupsId = new ArrayList<>();
    for (Group group : groupDao.getAll()) {
      groupsId.add(group.getGroupId());
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
        student.setGroupId(d);
        student.setFirstName(firstNames[randomInt(random, 0, firstNames.length - 1)]);
        student.setLastName(lastNames[randomInt(random, 0, lastNames.length - 1)]);
        studentList.add(student);
        studentsWithGroup++;
        i++;
      }
    }
    for (int i = 0; i < studTotal - studentsWithGroup; i++) {
      Student student = new Student();
      student.setFirstName(firstNames[randomInt(random, 0, firstNames.length - 1)]);
      student.setLastName(lastNames[randomInt(random, 0, lastNames.length - 1)]);
      studentList.add(student);
    }
    studentDao.add(studentList);
  }

  private void addStudentsCourses() {
    List<Course> courseList = courseDao.getAll();
    List<Student> studentList = studentDao.getStudent();
    List<Student> studentListNew = new ArrayList<>();
    BitSet bitSet = new BitSet(Integer.valueOf(coursesProp));
    for (Student student : studentList) {
      List<Course> courseListNew = new ArrayList<>();
      int k = randomInt(random,
          Integer.valueOf(studentsCoursesMin),
          Integer.valueOf(studentsCoursesMax));
      int i = 0;
      bitSet.clear();
      while (i < k) {
        int course = randomInt(random, 1,
            Integer.valueOf(coursesProp));
        if (!bitSet.get(course)) {
          bitSet.set(course);
          i++;
          courseListNew.add(courseList.get(course - 1));
        }
      }
      student.setCourseList(courseListNew);
      studentListNew.add(student);
    }
    studentDao.addStudentsCourse(studentListNew);
  }

  public void createAll()
      throws IOException, URISyntaxException {
    addGroups();
    addCourses();
    addStudents();
    addStudentsCourses();
  }
}
