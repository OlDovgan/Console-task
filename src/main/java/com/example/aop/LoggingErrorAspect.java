package com.example.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingErrorAspect {

  private final Logger logger
      = LoggerFactory.getLogger("com.example");

  @AfterThrowing(pointcut = "com.example.aop.AppPointCut.pointCutDao()", throwing = "ex")
  public void afterThrowingExampleAdvice(JoinPoint joinPoint, Throwable ex) {
    logger.error("Method {} failed with exception", joinPoint.getSignature().getName(), ex);
  }
}

