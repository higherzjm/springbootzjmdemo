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
    public void test1() {
        //���ַ������п�
        String name = "����";
        name = Optional.ofNullable(name).orElse("δ����ֵ");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElse("orElse->δ����ֵ");
        log.info("nam:{}", name);
        name = null;
        name = Optional.ofNullable(name).orElseGet(()-> "orElseGet->δ����ֵ");
        log.info("nam:{}", name);

         //�����ж�
        Student student1 = new Student(123, "����", 30);
        Optional<Integer> optional = Optional.ofNullable(student1).flatMap((e) -> Optional.of(e.getAge()));
        log.info("��һ��ѧ��������:" + optional.get());

         name = Optional.ofNullable(student1).map((v) -> v.getName()).get();
        log.info("��һ��ѧ��������:" + name);

        Student student2 = new Student(111, null, 30);
        //�����ָ������Ϊ�շ��ع̶�ֵ
        String name2 = Optional.ofNullable(student2).map(Student::getName).orElse("Ա��δע������");
        log.info("�ڶ���ѧ��������:" + name2);


        student2 = null;
        Student student3 = new Student(111, "����", 30);
         //������Ϊ��ֱ�ӷ����µĶ���
        String name3 = Optional.ofNullable(student2).orElseGet(() -> {
            return student3;
        }).getName();
        log.info("������ѧ��������:" + name3);
    }


}
