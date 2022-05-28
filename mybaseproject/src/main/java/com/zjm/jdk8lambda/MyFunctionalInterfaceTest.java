package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.lang.Nullable;


/**
 * @author zhujianming
 * FunctionalInterface  函数式接口
 */
@Slf4j
public class MyFunctionalInterfaceTest {

    @Test
    public void test1() {
        Students students = new Students();
        //定义需要传的参数值和函数名称
        String ret = replacePlaceholders("张三", 20, students::findName);
        log.info("函数式编程返回值：{}",ret);
    }

    private String replacePlaceholders(String name, Integer age, MyFunctionalInterfaceTest.PlaceholderResolver placeholderResolver) {
        name=name+"8080【调用函数里面修改】";
        return placeholderResolver.resolvePlaceholder(name, age);
    }

    /**
     * 被调用的类和方法
     */
    class Students {
        String findName(String name, Integer age) {
            return "姓名:" + name + ",年龄:" + age ;
        }
    }

    /**
     * 定义与被调用类绑定接口和接口函数
     */
    @FunctionalInterface
    public interface PlaceholderResolver {
        /**
         * 函数接口名称
         * @param name 11
         * @param age 22
         * @return
         */
        @Nullable
        String resolvePlaceholder(String name, Integer age);
    }
}
