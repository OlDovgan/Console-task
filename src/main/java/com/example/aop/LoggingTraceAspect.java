package com.example.aop;


import com.example.dao.CourseDao;
import com.example.dao.GroupDao;
import com.example.dao.StudentDao;
import com.example.service.CourseService;
import com.example.service.GroupService;
import com.example.service.StudentService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingTraceAspect {


  private final Logger loggerCourseService
      = LoggerFactory.getLogger(CourseService.class);
  private final Logger loggerGroupService
      = LoggerFactory.getLogger(GroupService.class);
  private final Logger loggerStudentService
      = LoggerFactory.getLogger(StudentService.class);

  private final Logger loggerStudentDao
      = LoggerFactory.getLogger(StudentDao.class);

  private final Logger loggerCourseDao
      = LoggerFactory.getLogger(CourseDao.class);

  private final Logger loggerGroupDao
      = LoggerFactory.getLogger(GroupDao.class);
  private static final String BEFORE = "Starting {} ";
  private static final String AFTER = "Method  {}  was executed successfully";

  @Before("com.example.aop.AppPointCut.pointCutCourseService()")
  public void beforeCourseServiceAdvice(JoinPoint joinPoint) {
    loggerCourseService.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutCourseService() &&"
      + "!com.example.aop.AppPointCut.pointCutCreate()")
  public void afterReturningCourseServiceAdvice(JoinPoint joinPoint) {
    loggerCourseService.trace(AFTER, joinPoint.getSignature().getName());
  }

  @Before("com.example.aop.AppPointCut.pointCutGroupService()")
  public void beforeGroupServiceAdvice(JoinPoint joinPoint) {
    loggerGroupService.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutGroupService() &&"
      + "!com.example.aop.AppPointCut.pointCutCreate()")
  public void afterReturningGroupServiceAdvice(JoinPoint joinPoint) {
    loggerGroupService.trace(AFTER, joinPoint.getSignature().getName());
  }

  @Before("com.example.aop.AppPointCut.pointCutStudentService()")
  public void beforeStudentServiceAdvice(JoinPoint joinPoint) {
    loggerStudentService.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutStudentService() &&"
      + "!com.example.aop.AppPointCut.pointCutCreate()")
  public void afterReturningStudentServiceAdvice(JoinPoint joinPoint) {

    loggerStudentService.trace(AFTER, joinPoint.getSignature().getName());
  }


  @Before("com.example.aop.AppPointCut.pointCutCourseDao()")
  public void beforeCourseDaoAdvice(JoinPoint joinPoint) {
    loggerCourseDao.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutCourseDao()")
  public void afterReturningCourseDaoAdvice(JoinPoint joinPoint) {
    loggerCourseDao.trace(AFTER, joinPoint.getSignature().getName());
  }

  @Before("com.example.aop.AppPointCut.pointCutGroupDao()")
  public void beforeGroupDaoAdvice(JoinPoint joinPoint) {
    loggerGroupDao.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutGroupDao()")
  public void afterReturningGroupDaoAdvice(JoinPoint joinPoint) {
    loggerGroupDao.trace(AFTER, joinPoint.getSignature().getName());
  }

  @Before("com.example.aop.AppPointCut.pointCutStudentDao()")
  public void beforeStudentDaoAdvice(JoinPoint joinPoint) {
    loggerStudentDao.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutStudentDao()")
  public void afterReturningStudentDaoAdvice(JoinPoint joinPoint) {
    loggerStudentDao.trace(AFTER, joinPoint.getSignature().getName());
  }
}

