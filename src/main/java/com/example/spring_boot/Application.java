package com.example.spring_boot;


import com.example.Data;
import com.example.SpringConfig;
import com.example.Table;
import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.menu.Menu;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application {

  public static final AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(
          SpringConfig.class);
  public static final StudentDao STUDENT_DAO = context.getBean(StudentDao.class);
  public static final SpringConfig SPRING_CONFIG = context.getBean(SpringConfig.class);
  public static final GroupDao GROUP_DAO = context.getBean(GroupDao.class);
  public static final CourseDao COURSE_DAO = context.getBean(CourseDao.class);
  public static final Data DATA = context.getBean(Data.class);
  public static final Table TABLE = context.getBean(Table.class);

  public static final JdbcTemplate JDBC_TEMPLATE =
      context.getBean("jdbcTemplate", JdbcTemplate.class);

  public static void main(String[] args) throws SQLException {
    SpringApplication.run(Application.class, args);

    Menu firstMenu = context.getBean("firstMenu", Menu.class);
    Menu secondMenu = context.getBean("secondMenu", Menu.class);
    firstMenu.executeMenu();
    context.close();
    while (true) {
      secondMenu.executeMenu();
    }
  }
}
