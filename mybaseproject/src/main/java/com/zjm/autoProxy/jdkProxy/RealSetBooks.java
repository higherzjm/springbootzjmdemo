package com.zjm.autoProxy.jdkProxy;

/*
 * RealSubject����ʵ�����ɫ����
 ????   �����˴����ɫ���������ʵ����
 */
public class RealSetBooks implements Books {

	public double price(Double money) {
		System.out.println("�����۸���:" + money);
		return money;
	}

}
