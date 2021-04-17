package com.zjm.jdk8lambda;

import com.zjm.jdk8lambda.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhujianming
 */
@Slf4j
public class MyConsumer {

    @Test
    public void test1() {
        List<Student> studentList = Arrays.asList(new Student(0, "����", 20), new Student(1, "����", 0));
        myConsumer1(studentList, s -> s.forEach(a ->
                //Ա������ǰ׺�����
                a.setName("2021-" + a.getName())));
        log.info("" + studentList);
    }

    public void myConsumer1(List<Student> studentsList, Consumer<List<Student>> consumer) {
        //Ա������ǰ׺���·�
        studentsList.forEach(s -> s.setName("11 " + s.getName()));
        consumer.accept(studentsList);
    }
}
