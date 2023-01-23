package com.example.service;

import com.example.FileReader;
import com.example.dao.GroupDao;
import com.example.model.Course;
import com.example.model.Group;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  @Value("${groups}")
  private String groupsNumber;
  @Value("${courses}")
  private String coursesNumber;
  @Value("${course-name-file}")
  private String fileCourseName;
  @Value("${students-total}")
  private String studentsTotalNumber;
  private Random random;
  private FileReader fileReader;
  public GroupDao groupDao;

  @Autowired
  public GroupService(GroupDao groupDao, Random random, FileReader fileReader) {
    this.groupDao = groupDao;
    this.random = random;
    this.fileReader = fileReader;
  }
  public void createData() {

    groupDao.add(createGroupsList());
  }

  private List<Group> createGroupsList() {

    List<Group> groupList = new ArrayList<>();
    for (int i = 0;
        i < Math.min(Integer.valueOf(groupsNumber), Integer.valueOf(studentsTotalNumber)); i++) {
      groupList.add(new Group(groupName(random, 2, 2)));
    }
    return groupList;
  }

  private String groupName(Random random, int characters, int digitCount) {
    return RandomStringUtils.random(characters, 0, 0, true, false, null, random) + "-"
        + RandomStringUtils.random(digitCount, 0, 0, false, true, null, random);
  }

  private List<Course> createCoursesList() throws IOException, URISyntaxException {

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

  public int randomInt(Random random, int origin, int bound) {
    if (origin >= bound) {
      throw new IllegalArgumentException();
    }
    return origin + random.nextInt(bound);
  }
  public void clear() {
    groupDao.clearAll();
  }

  public List<Group> getGroupsByStudentCount(int number){
   return groupDao.getGroupsByStudentCount(number);
  }
  public List<Group> getAll() {
    return groupDao.getAll();
  }
}
