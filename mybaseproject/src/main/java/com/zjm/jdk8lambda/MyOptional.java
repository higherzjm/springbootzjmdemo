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
    /**
     * 但属性判断
     */
    @Test
    public void test1_base() {
        //简单字符串的判空
        String name = "zhangsan";
        name = Optional.ofNullable(name).orElse("unKnow");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElse("orElse->unKnow");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElseGet(()-> "orElseGet->unKnow");
        log.info("nam:{}", name);
    }

    /**
     * 对象查询固定属性值
     */
    @Test
    public void test2_object_map(){
        String name;
        //对象判断
        Student student1 = new Student(123, "zhangsan", 30);
        Optional<Integer> optional = Optional.ofNullable(student1).flatMap((e) -> Optional.of(e.getAge()));
        log.info("name:" + optional.get());

        name = Optional.ofNullable(student1).map((v) -> v.getName()).get();
        log.info("name:" + name);

        Student student2 = new Student(111, null, 30);
        //对象的指定属性为空返回固定值
        String name2 = Optional.ofNullable(student2).map(Student::getName).orElse("unKnow");
        log.info("name2:" + name2);


    }
    /**
     * 空对象判断
     */
    @Test
    public void test3_object_null() throws Throwable{

        Student student =null;
        //当对象为空直接返回新的对象
        Student student2 = Optional.ofNullable(student).orElseGet(() -> {
            Student student3 = new Student(111, "lisi", 30);
            return student3;
        });
        log.info("student2:" + student2);

        //当对象为空直接抛异常,不为空直接返回对象
        Student student4 = Optional.ofNullable(student2).orElseThrow(() -> new Throwable("null"));
        log.info("student4:"+student4);
    }
    /**
     * 非空对象查询
     */
    @Test
    public void test4_object_null(){
        Student student1 = new Student(123, "wangwu", 30);
        //对象非空直接处理业务
        Optional.ofNullable(student1).ifPresent(u->doSomthing(student1));
    }
    private void doSomthing(Student student){
        log.info("student:"+student);
    }


}
