package com.zjm.autoProxy.cglibProxy;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;

/**
 * ��ʵ����
 * @author <u>sunlh</u>
 *
 */
@Slf4j
public class HelloWorld { 
     public void sayHelloWorld(String msg) {
           System.out.println("Hello World!"+msg);
     }

     public Student saveStudentInfo(Student student){
         System.out.println("��ӵ�ѧ����ϢΪ:"+student);
         return student;
     }
}
