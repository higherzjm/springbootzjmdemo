package com.zjm.my_classloader;

import lombok.extern.slf4j.Slf4j;
/**
 * sun的类加载器都一样，不管实例都一样
 */
@Slf4j
public class Test2 {
    public static void  main(String[] args){
        Test2 test1=new Test2();
        test1.test1();
        Test2 test2=new Test2();
        test2.test2();
    }

    /**
     * 同一实例类加载器都一样
     */
    public void test1(){
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        log.info("classLoader test1:"+classLoader);

        new Test2().test2();
    }
    /**
     * 同一实例类加载器都一样
     */
    public void test2(){
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        log.info("classLoader test2:"+classLoader);

    }
}
