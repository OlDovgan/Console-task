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

  private Logger loggerLayer
      = LoggerFactory.getLogger("com.example.layer");
  private Logger logger
      = LoggerFactory.getLogger("com.example");
  private Logger loggerDao
      = LoggerFactory.getLogger("com.example.dao");
  private Logger loggerService
      = LoggerFactory.getLogger("com.example.service");


  @Pointcut("execution (* com.example.layer.*.*.*(..))")
  public void pointCutLayer() {
  }

  @Pointcut("execution (* com.example.*.*.*.*(..))")
  public void pointCutExample() {
  }

  @Pointcut("execution (* com.example.layer.dao.*.*(..))")
  public void pointCutLayerDao() {}
  @Pointcut("execution (* com.example.layer.service.*.*(..))")
  public void pointCutLayerService() {}


  @Before("pointCutLayer()")
  public void beforeLayerAdvice(JoinPoint joinPoint) {
    loggerLayer.info("{}", joinPoint);
    loggerLayer.debug("{} ", joinPoint);
   }
  @Before("pointCutLayerDao()")
  public void beforeLayerDaoAdvice(JoinPoint joinPoint) {
     loggerDao.trace("{} ", joinPoint);
  }
  @Before("pointCutLayerService()")
  public void beforeLayerServiceAdvice(JoinPoint joinPoint) {
     loggerService.trace("{} ", joinPoint);
  }

  @AfterThrowing("pointCutExample()")
  public void beforeErrorAdvice(JoinPoint joinPoint) {
    loggerLayer.error("{} ", joinPoint);

  }
}
