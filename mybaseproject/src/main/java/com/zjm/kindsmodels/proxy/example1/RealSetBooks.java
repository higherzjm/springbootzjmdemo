package com.zjm.kindsmodels.proxy.example1;

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
