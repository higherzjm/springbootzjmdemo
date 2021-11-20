package com.zjm.springframework.methodInterceptor.interceptor1;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;

//@Configuration
public class InterceptorConfig {
 
    public static final String traceExecution = "execution(* com.hfi.aop..*.*(..))";
 
 
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