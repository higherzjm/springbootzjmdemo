package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.lang.Nullable;


/**
 * @author zhujianming
 * FunctionalInterface  ����ʽ�ӿ�
 */
@Slf4j
public class MyFunctionalInterface {

    @Test
    public void test1() {
        Students students = new Students();
        String ret = replacePlaceholders("����", 20, students::getName);//������Ҫ���Ĳ���ֵ�ͺ�������
        log.info("����ʽ��̷���ֵ��{}",ret);
    }

    public String replacePlaceholders(String name, Integer age, MyFunctionalInterface.PlaceholderResolver placeholderResolver) {
        name=name+"8080�����ú��������޸ġ�";
        return placeholderResolver.resolvePlaceholder(name, age);
    }
    //�����õ���ͷ���
    class Students {
        public String getName(String name, Integer age) {
            return "����:" + name + ",����:" + age ;
        }
    }
    //�����뱻������󶨽ӿںͽӿں���
    @FunctionalInterface
    public interface PlaceholderResolver {
        @Nullable
        String resolvePlaceholder(String name, Integer age);
    }
}
