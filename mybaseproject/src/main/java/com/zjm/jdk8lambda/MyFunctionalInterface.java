package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.lang.Nullable;


/**
 * @author zhujianming
 * FunctionalInterface  函数式接口
 */
@Slf4j
public class MyFunctionalInterface {

    @Test
    public void test1() {
        Students students = new Students();
        String ret = replacePlaceholders("张三", 20, students::getName);//定义需要传的参数值和函数名称
        log.info("函数式编程返回值：{}",ret);
    }

    public String replacePlaceholders(String name, Integer age, MyFunctionalInterface.PlaceholderResolver placeholderResolver) {
        name=name+"8080【调用函数里面修改】";
        return placeholderResolver.resolvePlaceholder(name, age);
    }
    //被调用的类和方法
    class Students {
        public String getName(String name, Integer age) {
            return "姓名:" + name + ",年龄:" + age ;
        }
    }
    //定义与被调用类绑定接口和接口函数
    @FunctionalInterface
    public interface PlaceholderResolver {
        @Nullable
        String resolvePlaceholder(String name, Integer age);
    }
}
