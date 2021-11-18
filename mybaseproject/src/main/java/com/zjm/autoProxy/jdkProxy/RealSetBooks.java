package com.zjm.autoProxy.jdkProxy;

/*
 * RealSubject（真实主题角色）：
 ????   定义了代理角色所代表的真实对象
 */
public class RealSetBooks implements Books {

	public double price(Double money) {
		System.out.println("price()被代理方法调用");
		return money;
	}
	public void price2() {
		System.out.println("price2()被代理方法调用");
	}
}
