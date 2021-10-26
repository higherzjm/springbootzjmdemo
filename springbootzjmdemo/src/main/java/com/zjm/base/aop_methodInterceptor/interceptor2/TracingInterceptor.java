package com.zjm.base.aop_methodInterceptor.interceptor2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        HfiTrace annotation = getAnnotation(method);
        if (annotation == null) {
            return null;
        }
        System.out.println("method " + invocation.getMethod() + " is called on " + invocation.getThis() + " with args" +
                " " + invocation.getArguments());
        Object proceed = invocation.proceed();
        System.out.println("method " + invocation.getMethod() + " returns " + proceed);
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