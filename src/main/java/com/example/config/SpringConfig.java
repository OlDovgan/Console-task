package com.example.config;


import com.example.Utility;
import com.example.menu.FirstMenu;
import com.example.menu.MainMenu;
import com.example.menu.Menu;
import com.example.menu.SecondMenu;
import com.example.model.Course;
import com.example.model.Group;
import com.example.model.Student;
import java.util.List;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class SpringConfig {

  @Bean
  public Random random() {
    return new Random();
  }

  @Bean
  public RowMapper<Student> mapperStudent() {
    return new BeanPropertyRowMapper<>(Student.class);
  }

  @Bean
  public RowMapper<Course> mapperCourse() {
    return new BeanPropertyRowMapper<>(Course.class);
  }

  @Bean
  public RowMapper<Group> mapperGroup() {
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

  @Bean
  public Utility utility() {
    return new Utility();
  }

  private MainMenu createMenu(List<Menu> items) {
    MainMenu menu = new MainMenu(utility());
    items.forEach(menu::addMenuItem);
    return menu;
  }
}
