package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;


/**
 * @author zhujianming
 * FunctionalInterface  函数式接口
 */
@Slf4j
public class MyFunctionalInterface {

    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = new MyFunctionalInterface();
        myFunctionalInterface.test1();
    }


    public void test1() {
        Students students = new Students();
        String ret = replacePlaceholders("张三", 20, students::getName);//定义需要传的参数值和函数名称
        log.info("函数式编程返回值：{}",ret);
    }


    class Students {
        public String getName(String name, Integer age) {
            return "姓名:" + name + ",年龄:" + age ;
        }
    }

    public String replacePlaceholders(String name, Integer age, MyFunctionalInterface.PlaceholderResolver placeholderResolver) {
        name=name+"-->调用函数里面修改";
        return placeholderResolver.resolvePlaceholder(name, age);
    }

    @FunctionalInterface
    public interface PlaceholderResolver {
        @Nullable
        String resolvePlaceholder(String name, Integer age);
    }
}
