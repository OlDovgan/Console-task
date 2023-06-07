package com.example.extra;


import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestUtils {

  @Autowired
  private EntityManager entityManager;

  @Value("${courses}")
  private int coursesNumber;

  @Autowired
  GroupDao groupDao;
  @Autowired
  CourseDao courseDao;
  @Autowired
  private StudentService studentService;
  @Autowired
  CourseService courseService;
  @Autowired
  Random randomTest;
  @Autowired
  GroupService groupService;

  public boolean isExistStudentId(int studentId) {
    try {
      Object result = entityManager.createQuery("from  Student  where id = :id")
          .setParameter("id", studentId).getSingleResult();
      return ((Student) result).getId() == studentId;
    } catch (NoResultException noResult) {
      return false;
    }
  }

  public List<Course> getCourseList() {
    return entityManager.createQuery("from Course ", Course.class).getResultList();
  }

  public List<Group> getGroupList() {
    return groupDao.getAll();
  }

  public boolean isExistCourse(String name, String description) {

      List<Course> courses =  entityManager.createQuery("from  Course  where name = :name "
              + "and description = :description")
          .setParameter("name", name)
          .setParameter("description", description)
          .getResultList();
    return courses.size()>0;
  }

  public boolean isExistGroup(String groupName) {
    List<Group> groups =  entityManager.createQuery("from  Group  where name = :name", Group.class)
          .setParameter("name", groupName).getResultList();
    return groups.size() > 0;
  }

  public boolean isExistStudent(Student student) {
    return (entityManager.createQuery("from Student").getResultList().contains(student));
  }

  public boolean isExistStudent(List<Student> list) {
    return (entityManager.createQuery("from Student", Student.class).getResultList()
        .containsAll(list));
  }
@Transactional
  public boolean isExistStudentsCourse(int studentId, int courseId) {
    try {
      Student result = (Student) entityManager.createQuery("from  Student  where id = :id")
          .setParameter("id", studentId).getSingleResult();
      for (Course c : result.getCourses()) {
        if (c.getId() == courseId) {
          return true;
        }
      }
    } catch (NoResultException noResult) {
      return false;
    }
    return false;
  }

  public List<Student> createStudentList() {
    List<Group> groupList = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    String description = " English is a language—originally the language of the people of England";
    String description2 = " Probability theory is the branch of mathematics concerned with probability";
    Course first = new Course("English", description);
    Course second = new Course("Probability theory", description2);
    courses.add(first);
    courses.add(second);
    courseDao.add(courses);
    Group firstGroup = new Group();
    firstGroup.setName("cP-50");
    Group secondGroup = new Group();
    secondGroup.setName("Jp-04");
    Group thirdGroup = new Group();
    thirdGroup.setName("fG-08");
    groupList.add(firstGroup);
    groupList.add(secondGroup);
    groupList.add(thirdGroup);
    groupDao.add(groupList);
    Student student = new Student();
    student.setGroupId(1);
    student.setFirstName("Amir");
    student.setLastName("Watson");
    student.setCourses(courses);
    Student studentNext = new Student();
    studentNext.setGroupId(3);
    studentNext.setFirstName("Rex");
    studentNext.setLastName("Philip");
    studentNext.setCourses(new ArrayList<>());
    List<Student> studentListExpect = new ArrayList<>();
    studentListExpect.add(student);
    studentListExpect.add(studentNext);
    return studentListExpect;
  }

  @Value("${groups}")
  private int groupsNumber;
  @Value("${students-total}")
  private int studentsTotalNumber;

  public void createCourse() throws IOException, URISyntaxException {
    courseService.createData();
  }

  private List<Group> createGroupsList() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0; i < Math.min(groupsNumber, studentsTotalNumber); i++) {
      groupList.add(new Group(groupName(randomTest, 2, 2)));
    }
    return groupList;
  }

  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.random(characters, 0, 0, true, false, null, random) + "-"
        + RandomStringUtils.random(digitCount, 0, 0, false, true, null, random);
  }


  public Student createStudent(int groupId, String firstName, String lastName) {
    Student student = new Student();
    student.setGroupId(groupId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    return student;
  }

  @Transactional
  public void clearData() {
    String query = "TRUNCATE students, courses, groups, students_courses RESTART IDENTITY;";
    entityManager.createNativeQuery(query).executeUpdate();
  }

  @Transactional
  public void clearCourse() {
    String query = "TRUNCATE  courses RESTART IDENTITY CASCADE;";
    entityManager.createNativeQuery(query).executeUpdate();

  }

  public void createStudentInDb() throws IOException, URISyntaxException {
    groupService.createData();
    studentService.createData();
  }
}
