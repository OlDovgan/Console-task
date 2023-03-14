package com.example.service;

import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.model.Course;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

  private String fileName;
  private int coursesNumber;
  private FileReader fileReader;
  private CourseDao courseDao;

  @Autowired
  public CourseService(FileReader fileReader, CourseDao courseDao,
      @Value("${courses}") int coursesNumber, @Value("${course-name-file}") String fileName) {
    this.fileReader = fileReader;
    this.courseDao = courseDao;
    this.coursesNumber = coursesNumber;
    this.fileName = fileName;
  }

  public void createData() throws IOException, URISyntaxException {
    courseDao.clearAll();
    courseDao.add(createCoursesList(coursesNumber));
  }

  private List<Course> createCoursesList(int courseNum)
      throws IOException, URISyntaxException {
    String[] courseArray = createCourseArray(fileName);
    List<Course> courseList = new ArrayList<>();
    int i = 0;
    if (courseArray != null) {
      for (String course : courseArray) {
        String[] courseInfo = (course.trim().split("-", 2));
        courseList.add(new Course(courseInfo[0].trim(), courseInfo[1]));
        i++;
        if (i >= courseNum) {
          break;
        }
      }
    }
    return courseList;
  }

  private String[] createCourseArray(String coursesNameFile)
      throws FileNotFoundException, URISyntaxException {
    List<String> element = fileReader.readFile(coursesNameFile);
    String join = String.join(" ", element);
    return join.split(";");
  }

  public List<Course> getAll() {
    return courseDao.getAll();
  }
}
