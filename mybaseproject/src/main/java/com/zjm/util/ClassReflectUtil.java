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
        Student student=Student.builder().name("张三").age(20).build();
        System.out.println("s1："+student);
        ReflectUtil.setFieldValue(student,"name","李四");
        System.out.println("s2："+student);

        ReflectUtil.setFieldValue(student,ReflectUtil.getField(Student.class,"name"),"王五");
        System.out.println("s3："+student);

    }

    @Test
    public void test2(){
        Student student=Student.builder().name("张三").age(20).build();
        System.out.println("s1："+ReflectUtil.getFieldValue(student,"name"));

    }
}
