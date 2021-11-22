package com.zjm.autoProxy.jdkProxy.example2;

/**
 * @author zhujianming
 * @time 2021/11/22 9:49
 */
public class StudentProxy {
    public String getInfo(){
        return "学生信息";
    }
    public String getWithOneParam(String name){
        return String.format("学生，姓名:%s",name);
    }

    public String getInfoWithParam(String name,Integer age){
        return String.format("学生，姓名:{}，年龄:{}",name,age);
    }


}
