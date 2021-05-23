package com.zjm.javaReflect.example1;

import com.zjm.VO.Student;

/**
 * @author zhujianming
 */
public class MyClass2 {
    public String getName2(String name,Integer age){
        return name+age;
    }
    /*public String getName2(String name,String aliasName){
        return name+"->"+aliasName;
    }*/

    public Student getStudent2(Student student){
        return student;
    }
}
