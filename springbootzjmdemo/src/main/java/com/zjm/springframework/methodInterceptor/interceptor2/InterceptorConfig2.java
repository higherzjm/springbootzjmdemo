package com.zjm.springframework.methodInterceptor.interceptor2;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring aop 方法拦截器，根据自定义注解拦截
 */
@Configuration
public class InterceptorConfig2 {
 
   @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor3() {
        MyTracingInterceptor2 interceptor = new MyTracingInterceptor2();
 
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns("com.zjm.springframework.springtransaction.service.impl.StudentServiceImpl.*");
 
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}