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

    /**
     * 自定义函数调用
     */
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
    /**
     * 自定义函数校验抛异常
     */
    @Test
    public void test2() {
        int age=80;
        MyFunctionalInterfaceTest.checkAge(age).throwException("年龄需要100岁以内");
        log.info("验证通过");
    }
    private static ThrowException checkAge(int age) {
        return msg -> {
            if (age>=100) {
                throw new RuntimeException(msg);
            }
        };
    }
    /**
     * 函数式异常抛出
     **/
    @FunctionalInterface
    public interface ThrowException {

        /**
         * 抛出异常情况
         * @param msg 异常内容
         **/
        void throwException(String msg);

    }
}
