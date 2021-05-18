package com.zjm.util;

import cn.hutool.core.util.ReflectUtil;
import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhujianming
 */
@Slf4j
public class ClassReflectUtil {
    private static final Logger log2 = LoggerFactory.getLogger(ClassReflectUtil.class);

    @Test
    public void test1(){
        Student student=Student.builder().name("����").age(20).build();
        System.out.println("s1��"+student);
        ReflectUtil.setFieldValue(student,"name","����");
        System.out.println("s2��"+student);

        ReflectUtil.setFieldValue(student,ReflectUtil.getField(Student.class,"name"),"����");
        System.out.println("s3��"+student);
    }
}
