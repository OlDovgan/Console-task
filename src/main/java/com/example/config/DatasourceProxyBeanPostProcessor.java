package com.example.config;

import java.lang.reflect.Method;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {


  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof DataSource && !(bean instanceof ProxyDataSource)) {
      final ProxyFactory factory = new ProxyFactory(bean);
      factory.setProxyTargetClass(true);
      factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
      return factory.getProxy();
    }
    return bean;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  private static class ProxyDataSourceInterceptor implements MethodInterceptor {

    private final Logger logger
        = LoggerFactory.getLogger(this.getClass());
    private final DataSource dataSource;

    public ProxyDataSourceInterceptor(final DataSource dataSource) {
      this.dataSource = ProxyDataSourceBuilder.create(dataSource)
          .name("SchoolDS")
          .multiline()
          .logQueryBySlf4j(logger.getName())
          .build();
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
      final Method proxyMethod = ReflectionUtils.findMethod(this.dataSource.getClass(),
          invocation.getMethod().getName());
      if (proxyMethod != null) {
        return proxyMethod.invoke(this.dataSource, invocation.getArguments());
      }
      return invocation.proceed();
    }
  }
}
