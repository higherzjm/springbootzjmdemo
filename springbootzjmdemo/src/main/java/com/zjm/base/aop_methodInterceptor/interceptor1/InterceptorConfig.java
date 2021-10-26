package com.zjm.base.aop_methodInterceptor.interceptor1;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * spring aop 方法拦截器，根据指定路径全部拦截
 */
//@Configuration
public class InterceptorConfig {
 
    public static final String traceExecution = "execution(* com.zjm.springtransaction.service.impl..*.*(..))";
 
 
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor2() {
        TracingInterceptor interceptor = new TracingInterceptor();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(traceExecution);
 
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}