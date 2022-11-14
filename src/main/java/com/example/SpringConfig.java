package com.example;


import com.example.menu.MainMenu;
import com.example.menu.MainMenu.FirstMenu;
import com.example.menu.MainMenu.SecondMenu;
import com.example.menu.Menu;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.util.List;
import java.util.Random;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@SpringBootConfiguration
@ComponentScan("com.example")
@PropertySource("classpath:application.properties")
public class SpringConfig {

  @Bean
  public Random random() {
    return new Random();
  }
  @Bean
  public BeanPropertyRowMapper<Student> mapperStud() {
    return new BeanPropertyRowMapper<>(Student.class);
  }

  @Bean
  public BeanPropertyRowMapper<Course> mapperCourse() {
    return new BeanPropertyRowMapper<>(Course.class);
  }

  @Bean
  public BeanPropertyRowMapper<Group> mapperGroup() {
    return new BeanPropertyRowMapper<>(Group.class);
  }

  @Bean
  public MainMenu firstMenu(@FirstMenu List<Menu> items) {
    return createMenu(items);
  }

  @Bean
  public MainMenu secondMenu(@SecondMenu List<Menu> items) {
    return createMenu(items);
  }

  private MainMenu createMenu(List<Menu> items) {
    MainMenu menu = new MainMenu(new Settings());
    items.forEach(menu::addMenuItem);
    return menu;
  }
}
