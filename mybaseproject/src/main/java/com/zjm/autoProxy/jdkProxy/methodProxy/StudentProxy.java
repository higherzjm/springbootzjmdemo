package com.zjm.autoProxy.jdkProxy.methodProxy;

/**
 * @author zhujianming
 * @time 2021/11/22 9:49
 */
public class StudentProxy {
    public String noParam(){
        return "学生信息";
    }
    public String withOneParam(String name){
        return String.format("学生，姓名:%s",name);
    }

    public String withTwoParam(String name,Integer age){
        return String.format("学生，姓名:%s，年龄:%s",name,age);
    }


}
