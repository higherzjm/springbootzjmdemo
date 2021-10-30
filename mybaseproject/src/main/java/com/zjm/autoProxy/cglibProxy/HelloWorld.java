package com.zjm.autoProxy.cglibProxy;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;

/**
 * 真实对象
 * @author <u>sunlh</u>
 *
 */
@Slf4j
public class HelloWorld { 
     public void sayHelloWorld(String msg) {
           System.out.println("Hello World!"+msg);
     }

     public Student saveStudentInfo(Student student){
         System.out.println("添加的学生信息为:"+student);
         return student;
     }
}
