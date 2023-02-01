package com.example.service;

import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.model.Course;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  @Value("${course-name-file}")
  private String fileCourseName;
  @Value("${courses}")
  private int coursesNumber;
  private FileReader fileReader;
private  CourseDao courseDao;
  @Autowired
  public CourseService(FileReader fileReader, CourseDao courseDao) {
    this.fileReader = fileReader;
    this.courseDao = courseDao;
  }
  public void createNewData() throws IOException, URISyntaxException {
    courseDao.clearAll();
    courseDao.add(createCoursesList());
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
      if (i >= coursesNumber) {
        break;
      }
    }
    return coursesMap;
  }
  public List<Course> getAll() {
    return courseDao.getAll();
  }
}
