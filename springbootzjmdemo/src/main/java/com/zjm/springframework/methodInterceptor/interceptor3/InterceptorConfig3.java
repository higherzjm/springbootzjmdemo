package com.zjm.springframework.methodInterceptor.interceptor3;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring aop 方法拦截器，根据自定义注解拦截
 */
@Configuration
public class InterceptorConfig3 {
 
   @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor3() {
        MyTracingInterceptor3 interceptor = new MyTracingInterceptor3();
 
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns("com.zjm.springframework.springtransaction.service.impl.StudentServiceImpl.*");
 
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}