package com.zjm.kindsmodels.proxy;

/*
 * Proxy（代理主题角色）： 含有对真实主题角色的引用，代理角色通常在将客户端调用传递给真是主题对象之前或者之后执行某些操作，而不是单纯返回真实的对象。
 */
public class ProxySetBooks implements Books {
	private RealSetBooks realSetBooks;

	public ProxySetBooks() {
		realSetBooks = new RealSetBooks();
	}

	public double price(Double money) {
		System.out.println("售前处理");
		Double price = realSetBooks.price(money) * 0.8;
		System.out.println("打八折");
		return price;
	}

}
