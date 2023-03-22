package com.example.aop;

import org.aspectj.lang.annotation.Pointcut;

public class AppPointCut {

  @Pointcut("execution (* com.example.dao.*.*(..))")
  public void pointCutDao() {
  }

  @Pointcut("execution (* com.example.service.*.*(..))")
  public void pointCutService() {
  }

  @Pointcut("pointCutDao() || pointCutService()")
  public void pointCutServiceDao() {
  }
  @Pointcut("execution (* com.example.service.CourseService.create*(..))")
  public void pointCutCourseServiceCreate() {
  }

  @Pointcut("execution (* com.example.service.GroupService.create*(..))")
  public void pointCutGroupServiceCreate() {
  }

  @Pointcut("execution (* com.example.service.StudentService.create*(..))")
  public void pointCutStudentServiceCreate() {
  }

  @Pointcut("pointCutCourseServiceCreate() ||"
      + " pointCutGroupServiceCreate()|| pointCutStudentServiceCreate()")
  public void pointCutCreate() {
  }

}
