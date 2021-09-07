package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * �������ʽ
 */
@Slf4j
public class My_Predicate {
    Predicate<String> predicate1 = myStr ->
            (myStr.startsWith("name") || myStr.contains("����"));//�������壬�ַ�����ʼΪname�����������ΪTRUE

    @Test
    public void test1() {
        check("�ҵ�����������", predicate1);
        check("�ҵ�����������", predicate1);
    }

    public void check(String myStr, Predicate<String> filter) {
        if (filter.test(myStr)) {
            log.info("�ַ���'{}'У��ͨ��", myStr);
        } else {
            log.info("�ַ���'{}'У�鲻ͨ��", myStr);
        }
    }
}