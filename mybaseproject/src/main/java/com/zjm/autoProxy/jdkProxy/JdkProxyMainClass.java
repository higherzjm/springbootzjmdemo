package com.zjm.autoProxy.jdkProxy;

import java.lang.reflect.Proxy;
/**
 * @Description:  jdk ��̬����
 * @Author: zhujianming
 * @Date: 2021/5/25
 *  JDK��̬���������÷����������һ��ʵ�ִ���ӿڵ������࣬�ڵ��þ��巽��ǰ����InvokeHandler������
 * ��cglib��̬����������asm��Դ�����Դ���������class�ļ����ؽ�����ͨ���޸����ֽ�����������������
 *
 **/
public class JdkProxyMainClass {


    public static void main(String[] args) {
        autoProxy();//��̬����
    }


    public static void autoProxy() {
        /*
         * ��̬����
         */
        RealSetBooks realSetBooks = new RealSetBooks();
        AutoProxy autoProxy = new AutoProxy();
        autoProxy.setRealSetBooks(realSetBooks);
        Books proxySubject = (Books) Proxy.newProxyInstance(
                RealSetBooks.class.getClassLoader(), realSetBooks.getClass()
                        .getInterfaces(), autoProxy);
        proxySubject.price(100.0);
    }

}
