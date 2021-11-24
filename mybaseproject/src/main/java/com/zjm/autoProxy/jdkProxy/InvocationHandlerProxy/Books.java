package com.zjm.autoProxy.jdkProxy.InvocationHandlerProxy;

/*
 * subject（抽象主题角色）：
       真实主题与代理主题的共同接口。
 */
public interface Books {
	public double price(Double money);
	public void price2();

}
