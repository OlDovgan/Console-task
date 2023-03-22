package com.example.aop;


import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingInfoAspect {

  private final Logger loggerCourseService
      = LoggerFactory.getLogger(CourseService.class);
  private final Logger loggerGroupService
      = LoggerFactory.getLogger(GroupService.class);
  private final Logger loggerStudentService
      = LoggerFactory.getLogger(StudentService.class);

  @AfterReturning("com.example.aop.AppPointCut.pointCutCourseServiceCreate()")
  public void beforeCourseService() {
    loggerCourseService.info("Method  createData  was executed successfully");
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutGroupServiceCreate()")
  public void beforeGroupService() {
    loggerGroupService.info("Method  createData  was executed successfully");
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutStudentServiceCreate()")
  public void beforeStudentService() {
    loggerStudentService.info("Method  createData  was executed successfully");
  }
}

