package com.zjm.base.aop_methodInterceptor.interceptor1;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * spring aop 方法拦截器，根据指定路径全部拦截
 */
@Configuration
public class InterceptorConfig1 {
 
    public static final String traceExecution = "execution(* com.zjm.springtransaction.service.impl..*.updateIdentityUnTransaction(..))";
 
 
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor1() {
        MyTracingInterceptor1 interceptor = new MyTracingInterceptor1();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(traceExecution);
 
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}