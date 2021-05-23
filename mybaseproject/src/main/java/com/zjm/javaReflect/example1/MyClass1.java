package com.zjm.javaReflect.example1;

import com.zjm.VO.Student;

/**
 * @author zhujianming
 */
public class MyClass1 {
    public String getName1(String name,Integer age){
        return name+":"+age;
    }
  /*  public String getName1(String name,String aliasName){
        return name+"->"+aliasName;
    }*/

    public Student getStudent1(Student student){
        return student;
    }
}
