package com.zjm.kindsmodels.proxy.example1;

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

		System.out.println("��ǰ����");

		try {
			result = method.invoke(realSetBooks, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		System.out.println("�����");
		System.out.println("�Զ�����"+(Double) result * 0.8+"Ԫ");
		return result;
	}

}
