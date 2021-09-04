package com.zjm.my_classloader;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * sun的类加载器都一样，不管实例都一样
 */
@Slf4j
public class Test1 {
    public static void  main(String[] args){
        Test1 test1=new Test1();
        test1.test1();
        Test1 test2=new Test1();
        test2.test2();
    }

    /**
     * 同一实例类加载器都一样
     */
    @Test
    public void test1(){
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        log.info("classLoader test1:"+classLoader);

        new Test1().test2();
    }
    /**
     * 同一实例类加载器都一样
     */
    @Test
    public void test2(){
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        log.info("classLoader test2:"+classLoader);

    }
}
