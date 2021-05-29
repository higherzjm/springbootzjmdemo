package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;


/**
 * @author zhujianming
 * FunctionalInterface  ����ʽ�ӿ�
 */
@Slf4j
public class MyFunctionalInterface {

    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = new MyFunctionalInterface();
        myFunctionalInterface.test1();
    }


    public void test1() {
        Students students = new Students();
        String ret = replacePlaceholders("����", 20, students::getName);//������Ҫ���Ĳ���ֵ�ͺ�������
        log.info("����ʽ��̷���ֵ��{}",ret);
    }


    class Students {
        public String getName(String name, Integer age) {
            return "����:" + name + ",����:" + age ;
        }
    }

    public String replacePlaceholders(String name, Integer age, MyFunctionalInterface.PlaceholderResolver placeholderResolver) {
        name=name+"-->���ú��������޸�";
        return placeholderResolver.resolvePlaceholder(name, age);
    }

    @FunctionalInterface
    public interface PlaceholderResolver {
        @Nullable
        String resolvePlaceholder(String name, Integer age);
    }
}
