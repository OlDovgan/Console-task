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
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.example")
@PropertySource("classpath:DB.properties")
@Getter
public class SpringConfig {

  //  public @Value("${db-url}") String url;
//  @Value("${db-username}")
//  private String user;
//  @Value("${db-password}")
//  private String password;
//  @Value("${driver}")
//  private String driver;
//
//    @Bean
//  public DataSource dataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    dataSource.setDriverClassName(driver);
//    dataSource.setUrl(url);
//    dataSource.setUsername(user);
//    dataSource.setPassword(password);
//    return dataSource;
//  }
  @Autowired

  DataSource dataSource;

  @Bean
  @PostConstruct
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource);
  }

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
