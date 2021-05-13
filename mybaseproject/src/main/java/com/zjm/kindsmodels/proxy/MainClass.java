package com.zjm.kindsmodels.proxy;

import java.lang.reflect.Proxy;

public class MainClass {

    /**
     * Proxy模式又叫做代理模式，是构造型的设计 模式之一，它可以为其他对象提供一种代理（Proxy）以 控制对这个对象的访问。
     * 所谓代理，是指具有与代理元（被代理的对象）具有 相同的接口的类，客户端必须通过代理与被代理的目标 类交互，而代理一般在交互的过程中（交互前后），进
     * 行某些特别的处理。
     */
    public static void main(String[] args) {


        staticProxy();//手动代理
        autoProxy();//动态代理
    }

    /*
     * 手动代理
     */
    public static void staticProxy() {
        ProxySetBooks proxySetBooks = new ProxySetBooks();
        System.out.println("代理售后价格+" + proxySetBooks.price(100.0));
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
