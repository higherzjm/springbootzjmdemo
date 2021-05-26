package com.zjm.autoProxy.cglibProxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author zhujianming
 */
public class CglibMethodInterceptor2 {
    private Callback callback;

    public CglibMethodInterceptor2(Callback callback) {
        this.callback = callback;
    }

    /**
     * 生成CGLIB代理对象
     *
     * @param cls Class类
     * @return Class类的CGLIB代理对象
     */
    Object getProxy2(Class cls) {
        // CGLIB <u>enhancer</u>增强类对象
        Enhancer enhancer = new Enhancer();
        // 设置增强类型
        enhancer.setSuperclass(cls);
        // 定义代理逻辑对象,要求当前对象实现MethodInterceptor接口
        enhancer.setCallback(callback);
        // 生成并返回代理对象
        return enhancer.create();
    }
}
