package com.zjm.autoProxy.cglibProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor1 implements MethodInterceptor {

    /**
     * 生成CGLIB代理对象
     *
     * @param cls Class类
     * @return Class类的CGLIB代理对象
     */
    Object getProxy(Class cls) {
        // CGLIB <u>enhancer</u>增强类对象
        Enhancer enhancer = new Enhancer();
        // 设置增强类型
        enhancer.setSuperclass(cls);
        // 定义代理逻辑对象为当前对象，要求当前对象实现MethodInterceptor方法
        enhancer.setCallback(this);
        // 生成并返回代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用真实对象前");
        // CGLIB反射调用真实对象方法
        Object result = methodProxy.invokeSuper(proxy, args);
        System.out.println("调用真实对象后");
        System.out.println("");
        return result;
    }
}
