package com.zjm.autoProxy.cglibProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理拦截器1
 */
public class CglibInterceptor1 implements MethodInterceptor {



    @Override
    public Object intercept(Object proxy, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CglibInterceptor1--->调用真实对象前");
        // CGLIB反射调用真实对象方法
        Object result = methodProxy.invokeSuper(proxy, args);
        System.out.println("CglibInterceptor1--->调用真实对象后");
        System.out.println("");
        return result;
    }
}
