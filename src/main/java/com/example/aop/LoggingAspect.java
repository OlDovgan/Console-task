package com.example.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {


  private final Logger loggerExample
      = LoggerFactory.getLogger("com.example");
  private final Logger loggerDao
      = LoggerFactory.getLogger("com.example.dao");
  private final Logger loggerService
      = LoggerFactory.getLogger("com.example.service");

  @Pointcut("execution (* com.example.*.*.*(..))")
  public void pointCutExample() {
  }

  @Pointcut("execution (* com.example.dao.*.*(..))")
  public void pointCutDao() {
  }

  @Pointcut("execution (* com.example.service.*.*(..))")
  public void pointCutService() {
  }

  @Before("pointCutDao()")
  public void beforeDaoAdvice(JoinPoint joinPoint) {
    loggerDao.debug("{} ", joinPoint);
    loggerDao.info("{} ", joinPoint);
   }

  @Before("pointCutService()")
  public void beforeServiceAdvice(JoinPoint joinPoint) {
    loggerService.debug("{} ", joinPoint);
    loggerService.info("{} ", joinPoint);
  }
  @AfterThrowing(pointcut = "pointCutExample()", throwing = "ex")
  public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
    loggerExample.error("Method {} failed with exception", joinPoint.getSignature().getName(), ex);
  }
}

