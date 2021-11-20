package com.zjm.kindsmodels.proxy.example2;

import com.zjm.baseapplication.VO.Student;

public class MyProxyImpl implements IMyProxy {
    public void print(int num) {
        System.out.println("��ӡ" + num + "�ţ�");
    }

    @Override
    public void register(Student student) {
        System.out.println("ע���ѧ����Ϣ��:" + student);
    }
}