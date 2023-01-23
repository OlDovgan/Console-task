package com.example.dao.test;


<<<<<<< HEAD
import com.example.Data;
=======
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import com.example.FileReader;
import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
<<<<<<< HEAD
=======
import com.example.service.CourseService;
import com.example.service.Data;
import com.example.service.GroupService;
import com.example.service.StudentService;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@TestConfiguration
@PropertySource("classpath:generation.properties")
public class StudentDaoTestConfig {
<<<<<<< HEAD
  @Autowired
  private GroupDao groupDao;
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private StudentDao studentDao;
  @Autowired
  private FileReader fileReader;
=======

  @Autowired
  GroupDao groupDao;
  @Autowired
  CourseDao courseDao;
  @Autowired
  StudentDao studentDao;
  @Autowired
  FileReader fileReader;
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)

  @Bean
  @Primary
  @Scope("prototype")
  public Random randomTest() {
    return new Random(42);
  }

<<<<<<< HEAD
=======
  @Autowired
  CourseService courseService;

  @Bean
  @Primary
  @Scope("prototype")
  public GroupService groupService() {
    return new GroupService(groupDao, randomTest(), fileReader);
  }

  @Bean
  @Primary
  @Scope("prototype")
  public StudentService studentService() {
    return new StudentService(fileReader, courseDao, groupDao, studentDao, randomTest());
  }

>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  @Bean
  @Primary
  @Scope("prototype")
  public Data data() {
<<<<<<< HEAD
    return new Data(randomTest(),groupDao,courseDao,studentDao,fileReader);
=======
    return new Data(courseService, groupService(), studentService());
>>>>>>> 014d8fb (22_01_2023_01_03_PM_Task_2_3)
  }
}
