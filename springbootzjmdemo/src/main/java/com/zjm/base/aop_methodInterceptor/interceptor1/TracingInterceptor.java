package com.zjm.base.aop_methodInterceptor.interceptor1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TracingInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation i) throws Throwable {
        System.out.println("method " + i.getMethod() + " is called on " +
                i.getThis() + " with args " + i.getArguments());
        final Object proceed = i.proceed();
        Object ret = proceed;
        System.out.println("method " + i.getMethod() + " returns " + ret);
        return ret;
    }
}