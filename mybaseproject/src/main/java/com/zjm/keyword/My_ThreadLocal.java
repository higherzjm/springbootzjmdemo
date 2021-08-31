package com.zjm.keyword;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class My_ThreadLocal {
    //单线程数据全局共享，可以达到方法少传参数的目标,源码默认以线程id作为主键
    public ThreadLocal<Student> studentThreadLocal=new ThreadLocal<>();
    public static void main(String[] args){
        My_ThreadLocal threadLocal=new My_ThreadLocal();
        threadLocal.test1();
        threadLocal.test2();
    }
    public void test1(){
        Student student=Student.builder().name("张三zhsangsan").age(30).build();
        studentThreadLocal.set(student);
    }
    public void test2(){
      log.info("student info:"+studentThreadLocal.get());
    }
}
