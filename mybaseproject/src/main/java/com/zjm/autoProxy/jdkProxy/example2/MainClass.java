package com.zjm.autoProxy.jdkProxy.example2;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author zhujianming
 * @time 2021/11/22 9:57
 */
@Slf4j
public class MainClass {
    public static void main(String[] args) throws Exception {
        MainClass.class.newInstance().noParamProxy();
        MainClass.class.newInstance().withParamProxy();
    }

    private void noParamProxy() throws Exception {
        Method[] methods = StudentProxy.class.getMethods();
        String ret = (String) methods[0].invoke(StudentProxy.class.newInstance(), null);
        log.info("ret:{}",ret);
    }
    private void withParamProxy() throws Exception {
        Class<StudentProxy> theClass=StudentProxy.class;
        //Method method = theClass.getMethod("getWithOneParam",String[].class);
        Method[] methods = StudentProxy.class.getMethods();
        String ret = (String) methods[1].invoke(StudentProxy.class.newInstance(), new String[]{"张三"});
        log.info("ret:{}",ret);
    }
}