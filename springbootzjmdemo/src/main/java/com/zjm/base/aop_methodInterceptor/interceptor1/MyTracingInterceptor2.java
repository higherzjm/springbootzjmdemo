package com.zjm.base.aop_methodInterceptor.interceptor1;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
@Slf4j
public class MyTracingInterceptor2 implements MethodInterceptor {
    public Object invoke(MethodInvocation i) throws Throwable {
        log.info("根据指定路径扫描路径");
        log.info("method " + i.getMethod() + " is called on " +
                i.getThis() + " with args " + i.getArguments());
        final Object proceed = i.proceed();
        Object ret = proceed;
        log.info("method " + i.getMethod() + " returns " + ret);
        return ret;
    }
}