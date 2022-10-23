package com.example;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@Configuration
@ComponentScan("com.example")
@PropertySource("classpath:DB.properties")
public class SpringConfig {

  protected final ApplicationContext applicationContext;

  @Autowired
  public SpringConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Value("${db-url}")
  public String url;
  @Value("${db-username}")
  private String user;
  @Value("${db-password}")
  private String password;
  @Value("${driver}")
  private String driver;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(password);
    return dataSource;

  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }
}
