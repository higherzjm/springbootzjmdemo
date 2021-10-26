package com.zjm.base.aop_methodInterceptor.interceptor2;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
@Slf4j
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("根据指定路径和自定义注解拦截");
        Method method = invocation.getMethod();
        HfiTrace annotation = getAnnotation(method);
        if (annotation == null) {
            return null;
        }
        log.info("method " + invocation.getMethod() + " is called on " + invocation.getThis() + " with args" +
                " " + invocation.getArguments());
        Object proceed = invocation.proceed();
        log.info("method " + invocation.getMethod() + " returns " + proceed);
        return proceed;
    }
 
    private HfiTrace getAnnotation(Method method) {
        // 如果有多个annotation 似乎就不好用了 如放在controller上 由于已经有了@RequestMapping注解了 所以...
        if (method.isAnnotationPresent(HfiTrace.class)) {
            return method.getAnnotation(HfiTrace.class);
        }
        return null;
    }
}