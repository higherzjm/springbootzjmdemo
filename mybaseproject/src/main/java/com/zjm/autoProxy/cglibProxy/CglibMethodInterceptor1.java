package com.zjm.autoProxy.cglibProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor1 implements MethodInterceptor {

    /**
     * ����CGLIB�������
     *
     * @param cls Class��
     * @return Class���CGLIB�������
     */
    Object getProxy(Class cls) {
        // CGLIB <u>enhancer</u>��ǿ�����
        Enhancer enhancer = new Enhancer();
        // ������ǿ����
        enhancer.setSuperclass(cls);
        // ��������߼�����Ϊ��ǰ����Ҫ��ǰ����ʵ��MethodInterceptor����
        enhancer.setCallback(this);
        // ���ɲ����ش������
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("������ʵ����ǰ");
        // CGLIB���������ʵ���󷽷�
        Object result = methodProxy.invokeSuper(proxy, args);
        System.out.println("������ʵ�����");
        return result;
    }
}
