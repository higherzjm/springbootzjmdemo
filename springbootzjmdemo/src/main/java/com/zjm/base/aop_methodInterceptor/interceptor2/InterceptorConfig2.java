package com.zjm.base.aop_methodInterceptor.interceptor2;

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
    public DefaultPointcutAdvisor defaultPointcutAdvisor2() {
        MyTracingInterceptor interceptor = new MyTracingInterceptor();
 
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns("com.zjm.springtransaction.service.impl.*");
 
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}