package com.zjm.autoProxy.cglibProxy;
/**
 * ≤‚ ‘
 * @author sunlh
 *
 */
public class CglibProxyMainClass {
	public static void main(String[] args) {
		CglibProxyExample cpe = new CglibProxyExample();
		HelloWorld proxy = (HelloWorld) cpe.getProxy(HelloWorld.class);
		proxy.sayHelloWorld("’≈»˝");
	}
}
