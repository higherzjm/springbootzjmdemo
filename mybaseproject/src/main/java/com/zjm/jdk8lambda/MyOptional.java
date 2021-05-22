package com.zjm.jdk8lambda;

import com.zjm.VO.Student;
import org.junit.Test;

import java.util.Optional;

/**
 * @author zhujianming
 */
public class MyOptional {
    @Test
    public void  test1(){
        Student student1 = new Student(123, "张三", 30);
        Optional<Integer> optional = Optional.ofNullable(student1).flatMap((e) -> Optional.of(e.getAge()));
        System.out.println("第一个学生的年龄:" + optional.get());

        String name = Optional.ofNullable(student1).map((v)->v.getName()).get();
        System.out.println("第一个学生的姓名:" + name);

        Student student2 = new Student(111, null, 30);
        String name2 = Optional.ofNullable(student2).map(Student::getName).orElse("员工未注册姓名");
        System.out.println("第二个学生的名字:" + name2);

        student2 = null;
        Student student3 = new Student(111, "李四", 30);
        String name3 = Optional.ofNullable(student2).orElseGet(() -> {
            return student3;
        }).getName();
        System.out.println("第三个学生的名字:" + name3);
    }
}
