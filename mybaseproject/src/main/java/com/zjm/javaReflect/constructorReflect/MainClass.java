package com.zjm.javaReflect.constructorReflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * 构造器反射
 *
 * @author zhujianming
 * @time 2021/11/24 17:18
 */
@Slf4j
public class MainClass {
    private String name;
    private Integer age;

    public MainClass(String name, Integer age) {
        log.info("实例化MainClass有参数构造函数");
        this.name = name;
        this.age = age;
    }

  /*  public MainClass() {
        log.info("实例化MainClass无参数构造函数");
    }*/

    /**
     * 实例化午餐构造函数{只有一个无参构造器的类才有效}
     */
    @Test
    public void instantiateOnlyNoParam() throws Exception {
        Constructor<?> constructor = MainClass.class.getDeclaredConstructor();
        constructor.newInstance();
    }

    /**
     * 实例化午餐构造函数{只有一个无参构造器的类才有效}
     */
    @Test
    public void instantiateTwoParam() throws Exception {
        Constructor<?> constructor = MainClass.class.getConstructor(String.class,Integer.class);
        Object[] params=new Object[2];
        params[0]="张三";
        params[1]=25;
        constructor.newInstance(params);
    }

    /**
     * 实例化午餐构造函数
     */
    @Test
    public void instantiateNoParam() throws Exception {
        Constructor<?> constructor = MainClass.class.getConstructor();
        constructor.newInstance();
    }

}
