package com.zjm.jdk8lambda;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhujianming
 * Consumer  函数式接口应用
 */
@Slf4j
public class MyConsumer {

    @Test
    public void test1() {
        List<Student> studentList = Arrays.asList(new Student(0, "张三", 20), new Student(1, "李四", 0));
        myConsumer1(studentList, s -> s.forEach(a ->
                //员工姓名前缀加年份
                a.setName("2021-" + a.getName())));
        System.out.println("---" + studentList);
    }

    private void myConsumer1(List<Student> studentsList, Consumer<List<Student>> consumer) {
        //员工姓名前缀加月份
        studentsList.forEach(s -> s.setName("11 " + s.getName()));
        consumer.accept(studentsList);//回调到调用该方法的地方

    }

    @Test
    public void test2() {
        List<Student> studentList = Arrays.asList(new Student(0, "张三", 20), new Student(1, "李四", 0));
        myConsumer2(studentList, this::print,s->s.forEach(a->{
            a.setName(a.getName()+"--修改数据");
        }));
    }
    private void myConsumer2(List<Student> studentsList, Consumer<Student> consumer, Consumer<List<Student>> consumer2) {
        //员工姓名前缀加月份
        consumer2.accept(studentsList);
        studentsList.forEach(consumer);
    }
    private void print(Student student){
        log.info("递归打印student:"+student);
    }


}
