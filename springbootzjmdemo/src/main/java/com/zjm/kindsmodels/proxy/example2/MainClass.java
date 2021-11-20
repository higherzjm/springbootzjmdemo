package com.zjm.kindsmodels.proxy.example2;


import com.zjm.baseapplication.VO.Student;

public class MainClass {
    public static void main(String[] args) {
        ProxyHandler proxyHandler = new ProxyHandler();
        IMyProxy myProxy = (IMyProxy) proxyHandler.newProxyInstance(new MyProxyImpl());
        myProxy.print(10);
        Student student = Student.builder().name("张三").age(20).id(100).build();
        myProxy.register(student);
    }
}

