package com.zjm.jdk8lambda;

import com.zjm.VO.Student;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.function.Function;

@Slf4j
public class MyFunction {
    @Test
    public void test1(){
        // 先声明方法
        Function<Integer, Integer> funcDouble = (n) -> n * 2;
        Function<Integer, Integer> funcPlus2 = (n) -> n + 2;

        log.info("3*2:"+funcDouble.apply(3));//执行函数->3*2
        log.info("3+2:"+funcPlus2.apply(3));//执行函数->3+2

        log.info("(3*2)+2:"+funcDouble.andThen(funcPlus2).apply(3));// 先执行前面的函数再执行后面的函数->(3*2)+2
        log.info("(3+2)*5:"+funcDouble.compose(funcPlus2).apply(3));// 先执行后面的函数再执行前面的函数->(3+2)*5
        log.info("3*2:"+Function.identity().compose(funcDouble).apply(3));//获取函数定义执行对应的函数->3*2
        log.info("3+2:"+Function.identity().compose(funcPlus2).apply(3));//获取函数定义执行对应的函数->3+2

        //------定义函数并传递函数
        Function<Integer, Student> initStudentFunction=(n)->Student.builder().id(n).build();
        Function<String,String>  addStudentName=(s)->"学生姓名:"+s;
        initStudentInfo(initStudentFunction,addStudentName);
    }

    public void initStudentInfo(Function<Integer, Student> initStudentFunction, Function<String,String>  addStudentName){
        Student student=initStudentFunction.apply(110);
        String name=addStudentName.apply("张三");
        student.setName(name);
        log.info("student:"+student);
    }

    @Test
    public void test2(){
        Function<FutureTaskStudents,String> function=new Function<FutureTaskStudents,String>() {
            @Override
            public String apply(FutureTaskStudents o) {
                return o.getNameAge();
            }
        };
        String ret=getFunctionValue(function,FutureTaskStudents.builder().name("张三").age(100).build());
        System.out.println("ret:"+ret);
    }
    private String getFunctionValue(Function<FutureTaskStudents, String> function, FutureTaskStudents students){
        return function.apply(students);
    }
    @Data
    @Builder
    static class FutureTaskStudents {
        private String name;
        private Integer age;
        public String getNameAge(){
            return this.name+":"+age;
        }
    }

}

