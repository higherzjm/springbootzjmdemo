package com.zjm.kindsmodels.proxy.example2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {
    private Object targetObject;//������Ķ���
    //��������Ķ��������������������ʵ�ֽӿ���ΪProxy.newProxyInstance�����Ĳ�����
    public  Object newProxyInstance(Object targetObject){
        this.targetObject = targetObject;
        //targetObject.getClass().getClassLoader()�������������������
        //targetObject.getClass().getInterfaces()������������ʵ�ֽӿ�
        //this ��ǰ���󣬸ö���ʵ����InvocationHandler�ӿ�������invoke������ͨ��invoke�������Ե��ñ��������ķ���
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);
    }
    //�÷����ڴ��������÷���ʱ����
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("��¼��־");
        return method.invoke(targetObject,args);
    }
    public static void  main(String[] args){
        System.out.println("���Ǵ�������");
    }
}