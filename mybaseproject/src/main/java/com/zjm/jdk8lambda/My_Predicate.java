package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * 条件表达式
 */
@Slf4j
public class My_Predicate {
    Predicate<String> predicate1 = myStr ->
            (myStr.startsWith("name") || myStr.contains("张三"));//条件定义，字符串开始为name或包含张三才为TRUE

    @Test
    public void test1() {
        check("我的名字是李四", predicate1);
        check("我的名字是张三", predicate1);
    }

    public void check(String myStr, Predicate<String> filter) {
        if (filter.test(myStr)) {
            log.info("字符串'{}'校验通过", myStr);
        } else {
            log.info("字符串'{}'校验不通过", myStr);
        }
    }
}