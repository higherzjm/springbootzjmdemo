package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.BiPredicate;
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
        check("张三",30,(name,age)->{
              if (name.equals("张三")&&age==30){
                  return true;
              }else {
                  return false;
              }
        });
    }
   //单参数
    private void check(String myStr, Predicate<String> filter) {
        if (filter.test(myStr)) {
            log.info("字符串'{}'校验通过", myStr);
        } else {
            log.info("字符串'{}'校验不通过", myStr);
        }
    }
   //双参数
    private void check(String name,Integer age, BiPredicate<String,Integer> filter){
        if (filter.test(name,age)){
            log.info("姓名:{},年龄:{} 校验通过",name,age);
        }else {
            log.info("姓名:{},年龄:{} 校验不通过",name,age);
        }

    }

}