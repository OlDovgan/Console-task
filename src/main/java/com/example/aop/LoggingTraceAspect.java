package com.example.aop;


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


  private final Logger logger
      = LoggerFactory.getLogger("com.example");
  private static final String BEFORE = "Starting {} ";
  private static final String AFTER = "Method  {}  was executed successfully, {}";

  @Before("com.example.aop.AppPointCut.pointCutServiceDao()")
  public void beforeCourseServiceAdvice(JoinPoint joinPoint) {
    logger.trace(BEFORE, joinPoint);
  }

  @AfterReturning("com.example.aop.AppPointCut.pointCutServiceDao() &&"
      + "!com.example.aop.AppPointCut.pointCutCreate()")
  public void afterReturningCourseServiceAdvice(JoinPoint joinPoint) {
    logger.trace(AFTER, joinPoint.getSignature().getName(), joinPoint);
  }
}

