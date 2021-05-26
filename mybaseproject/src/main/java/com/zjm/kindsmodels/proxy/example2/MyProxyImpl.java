package com.zjm.kindsmodels.proxy.example2;

import com.zjm.VO.Student;

public class MyProxyImpl implements IMyProxy {
    public void print(int num) {
        System.out.println("打印" + num + "张！");
    }

    @Override
    public void register(Student student) {
        System.out.println("注册的学生信息是:" + student);
    }
}