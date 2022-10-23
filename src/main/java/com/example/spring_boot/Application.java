package com.example.spring_boot;


import com.example.SpringConfig;
import com.example.menu.AddNewStudent;
import com.example.menu.AddStudentToCourse;
import com.example.menu.CreateData;
import com.example.menu.DeleteStudent;
import com.example.menu.Exit;
import com.example.menu.FindAllGroups;
import com.example.menu.FindStudentsWithCourse;
import com.example.menu.MainMenu;
import com.example.menu.Next;
import com.example.menu.RemoveStudentFromCourse;
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
 public static final JdbcTemplate JDBC_TEMPLATE =
     context.getBean("jdbcTemplate", JdbcTemplate.class);

  public static void main(String[] args) throws SQLException {
    SpringApplication.run(Application.class, args);

    MainMenu firstMenu = context.getBean("mainMenu", MainMenu.class);
    MainMenu secondMenu = context.getBean("mainMenu", MainMenu.class);
    firstMenu(firstMenu);
    firstMenu.executeMenu();
    secondMenu(secondMenu);
		context.close();
    while (true) {
      secondMenu.executeMenu();
    }
  }
  public static void firstMenu(MainMenu menu) {
    menu.addMenuItem(context.getBean("createData", CreateData.class));
    menu.addMenuItem(context.getBean("next", Next.class));
  }

  public static void secondMenu(MainMenu menu) {
    menu.addMenuItem(context.getBean("findGroup", FindAllGroups.class));
    menu.addMenuItem(context.getBean("findStudWithCourse",FindStudentsWithCourse.class));
    menu.addMenuItem(context.getBean("addStudent", AddNewStudent.class));
    menu.addMenuItem(context.getBean("deleteStudent", DeleteStudent.class));
    menu.addMenuItem(context.getBean("addStuToCourse", AddStudentToCourse.class));
    menu.addMenuItem(context.getBean("removeStudentFromCourse", RemoveStudentFromCourse.class));
    menu.addMenuItem(context.getBean("exit", Exit.class));
  }
}
