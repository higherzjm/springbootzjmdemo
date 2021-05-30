package com.zjm.jdk8lambda;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.function.Function;

@Slf4j
public class MyFunction {
    @Test
    public void test1(){
        // ����������
        Function<Integer, Integer> funcDouble = (n) -> n * 2;
        Function<Integer, Integer> funcPlus2 = (n) -> n + 2;

        log.info("3*2:"+funcDouble.apply(3));//ִ�к���->3*2
        log.info("3+2:"+funcPlus2.apply(3));//ִ�к���->3+2

        log.info("(3*2)+2:"+funcDouble.andThen(funcPlus2).apply(3));// ��ִ��ǰ��ĺ�����ִ�к���ĺ���->(3*2)+2
        log.info("(3+2)*5:"+funcDouble.compose(funcPlus2).apply(3));// ��ִ�к���ĺ�����ִ��ǰ��ĺ���->(3+2)*5
        log.info("3*2:"+Function.identity().compose(funcDouble).apply(3));//��ȡ��������ִ�ж�Ӧ�ĺ���->3*2
        log.info("3+2:"+Function.identity().compose(funcPlus2).apply(3));//��ȡ��������ִ�ж�Ӧ�ĺ���->3+2

        //------���庯�������ݺ���
        Function<Integer, Student> initStudentFunction=(n)->Student.builder().id(n).build();
        Function<String,String>  addStudentName=(s)->"ѧ������:"+s;
        initStudentInfo(initStudentFunction,addStudentName);
    }

    public void initStudentInfo(Function<Integer, Student> initStudentFunction, Function<String,String>  addStudentName){
        Student student=initStudentFunction.apply(110);
        String name=addStudentName.apply("����");
        student.setName(name);
        log.info("student:"+student);
    }


}

