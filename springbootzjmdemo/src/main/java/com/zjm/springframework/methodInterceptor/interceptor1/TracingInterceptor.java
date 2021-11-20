package com.zjm.springframework.methodInterceptor.interceptor1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 方法拦截器
 */
public class TracingInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation i) throws Throwable {
        System.out.println("method " + i.getMethod() + " is called on " +
                i.getThis() + " with args " + i.getArguments());
        Object ret = i.proceed();
        System.out.println("method " + i.getMethod() + " returns " + ret);
        return ret;
    }

}