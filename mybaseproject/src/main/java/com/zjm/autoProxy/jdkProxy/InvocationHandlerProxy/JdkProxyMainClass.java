package com.zjm.autoProxy.jdkProxy.InvocationHandlerProxy;

import java.lang.reflect.Proxy;
/**
 * @Description:  jdk InvocationHandler动态代理
 * @Author: zhujianming
 * @Date: 2021/5/25
 *  JDK动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
 * 而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
 *
 **/
public class JdkProxyMainClass {


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
        proxySubject.price2();
    }

}
