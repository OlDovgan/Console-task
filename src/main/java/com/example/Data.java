package com.example;

import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.dao.StudentsCourseDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import com.example.model.StudentsCourse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class Data {

  private final FileReader fileReader = new FileReader();
  private static final String FIRST_NAME_FILE = "Firstname.txt";
  private static final String LAST_NAME_FILE = "Lastname.txt";
  private final Random random;

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

  private void addGroups(JdbcTemplate jdbcTemplate,Properties config) {
    GroupDao groupDao = new GroupDao(jdbcTemplate);
    List<Group> groupList = new ArrayList<>();
    for (int i = 0; i < Math.min(Integer.valueOf(config.getProperty("groups")),
        Integer.valueOf(config.getProperty("students-total"))); i++) {
      groupList.add(new Group(groupName(random, 2, 2),0));
    }
    groupDao.add(groupList);
  }

  private void addCourses(JdbcTemplate jdbcTemplate)

      throws  IOException, URISyntaxException {
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
    new CourseDao(jdbcTemplate).add(courseList);
  }

  private void addStudents( JdbcTemplate jdbcTemplate, Properties config)
      throws  IOException, URISyntaxException {
    GroupDao groupDao = new GroupDao(jdbcTemplate);
    List<Student> studentList = new ArrayList<>();
    int studTotal = Integer.valueOf(config.getProperty("students-total"));
    String[] firstNames = fileReader.readFile(FIRST_NAME_FILE).toArray(new String[]{});
    String[] lastNames = fileReader.readFile(LAST_NAME_FILE).toArray(new String[]{});
    List<Integer> groupsId = new ArrayList<>();
    for (Group group : groupDao.groupList()) {
      groupsId.add(group.getGroupId());
    }
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < groupsId.size(); i++) {
      set.add(groupsId.get((randomInt(random, 1, groupsId.size())) - 1));
    }
    int studentsWithGroup = 0;
    for (Integer d : set) {
      int number = randomInt(random,
          Integer.valueOf(config.getProperty("number-student-min")),
          Integer.valueOf(config.getProperty("number-student-max")));
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
    new StudentDao(jdbcTemplate).add(studentList);
  }

  private void addStudentsCourses(JdbcTemplate jdbcTemplate, Properties config) {
    StudentDao studentDao = new StudentDao(jdbcTemplate);
    StudentsCourseDao studentsCourseDao = new StudentsCourseDao(jdbcTemplate);
    List<StudentsCourse> studentsCourseList = new ArrayList<>();

    BitSet bitSet = new BitSet(Integer.valueOf(config.getProperty("courses")));

    for (Integer integer : studentDao.getId()) {
      int k = randomInt(random, Integer.valueOf(config.getProperty("students-courses-min")),
          Integer.valueOf(config.getProperty("students-courses-max")));
      int i = 0;
      bitSet.clear();

      while (i < k) {
        int course = randomInt(random, 1, Integer.valueOf(config.getProperty("courses")));
        if (!bitSet.get(course)) {
          bitSet.set(course);
          i++;
         studentsCourseList.add(new StudentsCourse(integer, course));
        }
      }
    }
    studentsCourseDao.add(studentsCourseList);
  }

  public void createAll(JdbcTemplate jdbcTemplate)
      throws IOException, URISyntaxException {
    Properties config = new Settings().setProperties("Task.properties");
    addGroups(jdbcTemplate, config);
    addCourses(jdbcTemplate);
    addStudents(jdbcTemplate,config);
    addStudentsCourses(jdbcTemplate,config);
  }
}
