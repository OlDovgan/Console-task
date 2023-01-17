package com.example;

import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.io.FileNotFoundException;
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
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


@Service
@PropertySource("classpath:generation.properties")
public class Data {

  @Value("${groups}")
  private String groupsNumber;

  @Value("${courses}")
  private String coursesNumber;
  @Value("${students-total}")
  private String studentsTotalNumber;
  @Value("${first-name-file}")
  private String fileFirstName;
  @Value("${last-name-file}")
  private String fileLastName;
  @Value("${course-name-file}")
  private String fileCourseName;
  @Value("${number-student-min}")
  private String numberStudentMin;
  @Value("${number-student-max}")
  private String numberStudentMax;
  @Value("${students-courses-min}")
  private String studentsCoursesMin;
  @Value("${students-courses-max}")
  private String studentsCoursesMax;

  private Random random;
  @Autowired
  private GroupDao groupDao;
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private StudentDao studentDao;
  @Autowired
  private FileReader fileReader;
  private int studentsWithGroup;
  @Autowired
  public Data(Random random) {
    this.random = random;
  }

  public  int randomInt(Random random, int origin, int bound) {
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

  private List<Group> createGroupsList() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0; i < Math.min(Integer.valueOf(groupsNumber),
        Integer.valueOf(studentsTotalNumber)); i++) {
      groupList.add(new Group( groupName(random, 2, 2)));
    }
    return groupList;
  }

  private List<Course> createCoursesList()
      throws IOException, URISyntaxException {

    String[] courseArray = createCourseArray(fileCourseName);
    Map<String, String> coursesMap = createCoursesMap(courseArray);

    List<Course> courseList = new ArrayList<>();
    for (Entry<String, String> entry : coursesMap.entrySet()) {
      courseList.add(new Course(entry.getKey(), entry.getValue()));
    }
    return courseList;

  }

  private String[] createCourseArray(String coursesNameFile)
      throws FileNotFoundException, URISyntaxException {
    List<String> element = fileReader.readFile(coursesNameFile);
    String join = String.join(" ", element);
    return join.split(";");
  }

  private Map<String, String> createCoursesMap(String[] coursesArray) {
    int i = 0;
    Map<String, String> coursesMap = new LinkedHashMap<>();
    for (String course : coursesArray) {
      String[] courseInfo = (course.trim().split("-", 2));
      coursesMap.put(courseInfo[0].trim(), courseInfo[1]);
      i++;
      if (i >= Integer.valueOf(coursesNumber)) {
        break;
      }
    }
    return coursesMap;
  }

  private List<Student> createStudentsList()
      throws IOException, URISyntaxException {
    String[] firstNames = fileReader.readFile(fileFirstName)
        .toArray(new String[]{});
    String[] lastNames = fileReader.readFile(fileLastName)
        .toArray(new String[]{});
    Set<Integer> set = setGroupId();
    List<Student> studentListWithGroup = createStudentListWithGroup(set, firstNames, lastNames);
    List<Student> studentListWithoutGroup = createStudentListWithoutGroup(firstNames, lastNames);

    return Stream.concat(studentListWithGroup.stream(),
        studentListWithoutGroup.stream()).toList();
  }

  public Set<Integer> setGroupId() {
    List<Integer> groupsId = new ArrayList<>();
    for (Group group : groupDao.getAll()) {
      groupsId.add(group.getId());
    }
    Set<Integer> groupsIdSet = new HashSet<>();
    for (int i = 0; i < groupsId.size(); i++) {
      groupsIdSet.add(groupsId.get((randomInt(random, 1, groupsId.size())) - 1));
    }
    return groupsIdSet;
  }


  private List<Student> createStudentListWithGroup(Set<Integer> set, String[] firstNames,
      String[] lastNames) {
    studentsWithGroup = 0;
    int studTotal = Integer.valueOf(studentsTotalNumber);
    List<Student> studentList = new ArrayList<>();

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
        for (Group group: groupDao.getAll()) {
          if (group.getId() == d) {
            student.setGroupName(group.getName());
          }
        }
        studentList.add(student);
        studentsWithGroup++;
        i++;
      }
    }
    return studentList;
  }

  private List<Student> createStudentListWithoutGroup(String[] firstNames, String[] lastNames) {
    int studTotal = Integer.valueOf(studentsTotalNumber);
    List<Student> studentList = new ArrayList<>();
    for (int i = 0; i < studTotal - studentsWithGroup; i++) {
      Student student = new Student();
      student.setFirstName(firstNames[randomInt(random, 0, firstNames.length - 1)]);
      student.setLastName(lastNames[randomInt(random, 0, lastNames.length - 1)]);
      studentList.add(student);
    }
    return studentList;
  }

  private List<Student> createStudentsCourseList() {
    List<Course> courseList = courseDao.getAll();
    BitSet bitSet = new BitSet(Integer.valueOf(coursesNumber));
    List<Student> studentListNew = new ArrayList<>();

    for (Student student : studentDao.getAll()) {
      List<Course> courseListAfterAdditionCourses = new ArrayList<>();
      int k = randomInt(random, Integer.valueOf(studentsCoursesMin),
          Integer.valueOf(studentsCoursesMax));
      int i = 0;
      bitSet.clear();

      while (i < k) {
        int course = randomInt(random, 1, Integer.valueOf(coursesNumber));
        if (!bitSet.get(course)) {
          bitSet.set(course);
          i++;
          courseListAfterAdditionCourses.add(courseList.get(course - 1));
        }

      }
      student.setCourse(courseListAfterAdditionCourses);
      studentListNew.add(student);
    }
    return studentListNew;
  }

  public void addAllData() throws IOException, URISyntaxException {
    groupDao.add(createGroupsList());
    courseDao.add(createCoursesList());
    studentDao.add(createStudentsList());
    studentDao.addStudentsCourse(createStudentsCourseList());
  }
}
