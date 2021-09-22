package com.zjm.autoProxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AutoProxy implements InvocationHandler {

	private RealSetBooks realSetBooks;

	public void setRealSetBooks(RealSetBooks realSetBooks) {
		this.realSetBooks = realSetBooks;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;

		System.out.println("invoke before");

		try {
			result = method.invoke(realSetBooks, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		System.out.println("invoke after");
		System.out.println("invoke value\"+(Double) result * 0.8+\"yuan");
		return result;
	}

}
