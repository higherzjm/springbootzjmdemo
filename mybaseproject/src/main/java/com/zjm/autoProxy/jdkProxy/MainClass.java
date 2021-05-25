package com.zjm.autoProxy.jdkProxy;

import java.lang.reflect.Proxy;

public class MainClass {


    public static void main(String[] args) {
        autoProxy();//动态代理
    }


    public static void autoProxy() {
        /*
         * 动态代理
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
