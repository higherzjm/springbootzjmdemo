package com.zjm.autoProxy.jdkProxy.methodProxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 类方法动态代理掉用
 * @author zhujianming
 * @time 2021/11/22 9:57
 */
@Slf4j
public class ReflectProxyMain {
    public static void main(String[] args) throws Exception {
        ReflectProxyMain main=new ReflectProxyMain();
        main.noParamProxy();
        main.onParamProxy();
        main.twoParamProxy();
    }

    /*
     * 不带参数方法代理
     * @throws Exception
     */
    private void noParamProxy() throws Exception {
        Method method = StudentProxy.class.getDeclaredMethod("noParam");
        String ret = (String)method.invoke(StudentProxy.class.newInstance(), null);
        log.info("ret:{}",ret);
    }
    /*
     *  * 带一个参数方法代理
     * @throws Exception
     */
    private void onParamProxy() throws Exception {
        Method method = StudentProxy.class.getDeclaredMethod("withOneParam",String.class);
        String ret = (String) method.invoke(StudentProxy.class.newInstance(), "张三");
        log.info("ret:{}",ret);
    }
    /**
     * 带2个参数方法代理
     * @throws Exception
     */
    private void twoParamProxy() throws Exception {
        Object[] args=new Object[2];
        args[0]="张三";
        args[1]=30;
        Method method = StudentProxy.class.getDeclaredMethod("withTwoParam",String.class,Integer.class);
        String ret = (String) method.invoke(StudentProxy.class.newInstance(), args);
        log.info("ret:{}",ret);
    }
}