package com.zjm.kindsmodels.proxy;

import java.lang.reflect.Proxy;

public class MainClass {

    /**
     * Proxyģʽ�ֽ�������ģʽ���ǹ����͵���� ģʽ֮һ��������Ϊ���������ṩһ�ִ���Proxy���� ���ƶ��������ķ��ʡ�
     * ��ν������ָ���������Ԫ��������Ķ��󣩾��� ��ͬ�Ľӿڵ��࣬�ͻ��˱���ͨ�������뱻�����Ŀ�� �ཻ����������һ���ڽ����Ĺ����У�����ǰ�󣩣���
     * ��ĳЩ�ر�Ĵ���
     */
    public static void main(String[] args) {


        staticProxy();//�ֶ�����
        autoProxy();//��̬����
    }

    /*
     * �ֶ�����
     */
    public static void staticProxy() {
        ProxySetBooks proxySetBooks = new ProxySetBooks();
        System.out.println("�����ۺ�۸�+" + proxySetBooks.price(100.0));
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
