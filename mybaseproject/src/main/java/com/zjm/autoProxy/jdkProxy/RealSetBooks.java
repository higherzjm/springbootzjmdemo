package com.zjm.autoProxy.jdkProxy;

/*
 * RealSubject（真实主题角色）：
 ????   定义了代理角色所代表的真实对象
 */
public class RealSetBooks implements Books {

	public double price(Double money) {
		System.out.println("出厂价格是:" + money);
		return money;
	}

}
