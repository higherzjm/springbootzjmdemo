package com.zjm.jdk8lambda;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;

/**
 * @author zhujianming
 */
@Slf4j
public class MyOptional {
    @Test
    public void test1() throws Throwable {
        //简单字符串的判空
        String name = "张三";
        name = Optional.ofNullable(name).orElse("未设置值");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElse("orElse->未设置值");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElseGet(()-> "orElseGet->未设置值");
        log.info("nam:{}", name);

         //对象判断
        Student student1 = new Student(123, "张三", 30);
        Optional<Integer> optional = Optional.ofNullable(student1).flatMap((e) -> Optional.of(e.getAge()));
        log.info("第一个学生的年龄:" + optional.get());

         name = Optional.ofNullable(student1).map((v) -> v.getName()).get();
        log.info("第一个学生的姓名:" + name);

        Student student2 = new Student(111, null, 30);
        //对象的指定属性为空返回固定值
        String name2 = Optional.ofNullable(student2).map(Student::getName).orElse("员工未注册姓名");
        log.info("第二个学生的名字:" + name2);


        student2 = null;
        Student student3 = new Student(111, "李四", 30);
         //当对象为空直接返回新的对象
        String name3 = Optional.ofNullable(student2).orElseGet(() -> {
            return student3;
        }).getName();
        log.info("第三个学生的名字:" + name3);


        Student student = Optional.ofNullable(student2).orElseThrow(() -> new Throwable("为空直接抛异常"));
        log.info("student:"+student);
    }




}
