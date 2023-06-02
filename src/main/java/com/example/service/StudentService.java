package com.example.service;

import com.example.FileReader;
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
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:generation.properties")
public class StudentService {

  private final Logger loggerStudentService
      = LoggerFactory.getLogger(StudentService.class);
  @Value("${first-name-file}")
  private String fileFirstName;
  @Value("${last-name-file}")
  private String fileLastName;
  @Value("${number-student-min}")
  private int numberStudentMin;
  @Value("${number-student-max}")
  private int numberStudentMax;
  @Value("${students-courses-min}")
  private int studentsCoursesMin;
  @Value("${students-courses-max}")
  private int studentsCoursesMax;
  @Value("${students-total}")
  private int studentsTotalNumber;
  @Value("${courses}")
  private int coursesNumber;
  private FileReader fileReader;
  private CourseDao courseDao;
  private GroupDao groupDao;
  private StudentDao studentDao;
  private Random random;
  private int studentsWithGroup;


  @Autowired
  public StudentService(FileReader fileReader, CourseDao courseDao, GroupDao groupDao,
      StudentDao studentDao, Random random) {
    this.fileReader = fileReader;
    this.courseDao = courseDao;
    this.groupDao = groupDao;
    this.studentDao = studentDao;
    this.random = random;
  }

  @Transactional
  public void createData() throws IOException, URISyntaxException {
    if (groupDao.getAll().isEmpty()) {
      loggerStudentService.error("No data available from group!!! Group table is empty");
      throw new IllegalArgumentException("No data available from group!!!");
    }
    if (courseDao.getAll().isEmpty()) {
      loggerStudentService.error("No data available from group!!! Course table is empty");
      throw new IllegalArgumentException("No data available from course!!!");
    } else {
      studentDao.clearAll();
      List<Student> list = createStudentsCourseList(createStudentsList());
      studentDao.add(list);
    }
  }


  private List<Student> createStudentsList() throws IOException, URISyntaxException {
    String[] firstNames = fileReader.readFile(fileFirstName).toArray(new String[]{});
    String[] lastNames = fileReader.readFile(fileLastName).toArray(new String[]{});
    Set<Integer> set = setGroupId();
    List<Student> studentListWithGroup = createStudentListWithGroup(set, firstNames, lastNames);
    List<Student> studentListWithoutGroup = createStudentListWithoutGroup(firstNames, lastNames);

    return Stream.concat(studentListWithGroup.stream(), studentListWithoutGroup.stream()).toList();
  }

  private Set<Integer> setGroupId() {
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

  private int randomInt(Random random, int origin, int bound) {
    if (origin >= bound) {
      throw new IllegalArgumentException("Error");
    }
    return origin + random.nextInt(bound);
  }

  private List<Student> createStudentListWithGroup(Set<Integer> set, String[] firstNames,
      String[] lastNames) {
    studentsWithGroup = 0;
    int studTotal = studentsTotalNumber;
    List<Student> studentList = new ArrayList<>();

    for (Integer d : set) {
      int number = randomInt(random, numberStudentMin, numberStudentMax);
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
    return studentList;
  }

  private List<Student> createStudentListWithoutGroup(String[] firstNames, String[] lastNames) {
    List<Student> studentList = new ArrayList<>();
    for (int i = 0; i < studentsTotalNumber - studentsWithGroup; i++) {
      Student student = new Student();
      student.setFirstName(firstNames[randomInt(random, 0, firstNames.length - 1)]);
      student.setLastName(lastNames[randomInt(random, 0, lastNames.length - 1)]);
      studentList.add(student);
    }
    return studentList;
  }

  private List<Student> createStudentsCourseList(List<Student> studentList) {
    List<Course> courseList = courseDao.getAll();
    BitSet bitSet = new BitSet(Integer.valueOf(coursesNumber));
    List<Student> studentListNew = new ArrayList<>();

    for (Student student : studentList) {
      int k = randomInt(random, studentsCoursesMin, studentsCoursesMax);
      int i = 0;
      bitSet.clear();

      while (i < k) {
        int course = randomInt(random, 1, coursesNumber);
        if (course > courseList.size()) {
          course = courseList.size();
          student.addCourseToStudent(courseList.get(course - 1));
          break;
        }
        if (!bitSet.get(course)) {
          bitSet.set(course);
          i++;
          student.addCourseToStudent(courseList.get(course - 1));
        }
      }
      studentListNew.add(student);
    }
    return studentListNew;
  }

  public List<Student> getWithCourse() {
    return studentDao.getWithCourse();
  }

  public List<Student> getWithCourse(String course) {
    return studentDao.getWithCourse(course);
  }

  public List<Student> getWithOutCourse(int courseId) {
    return studentDao.getWithOutCourse(courseId);
  }

  public List<Student> getAll() {
    return studentDao.getAll();
  }

  public void add(Student student) {
    studentDao.add(student);
  }

  public void add(List<Student> student) {
    studentDao.add(student);
  }

  public void delete(int id) {
    studentDao.deleteById(id);
  }

  public void deleteFromCourse(int studentId, int courseId) throws Exception {
    studentDao.deleteFromCourse(studentId, courseId);
  }

  public Student getStudentById(int id) throws Exception {
    loggerStudentService.debug("getStudentById({})", id);
    return studentDao.getStudentById(id);
  }

  public void addStudentsCourse(int studId, int courseNumber) throws Exception {
    loggerStudentService.debug("Start addStudentsCourse(" + studId + ", " + courseNumber + ")");
    studentDao.addStudentsCourse(studId, courseNumber);
  }
}
