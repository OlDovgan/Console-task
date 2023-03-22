package com.example.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingInfoAspect {

  private final Logger logger
      = LoggerFactory.getLogger("com.example");

  @AfterReturning("com.example.aop.AppPointCut.pointCutCreate()")
  public void afterReturningCourseService(JoinPoint joinPoint) {
    logger.info("Method {}  was executed successfully,{}",
        joinPoint.getSignature().getName(), joinPoint.getSignature());
  }
}

