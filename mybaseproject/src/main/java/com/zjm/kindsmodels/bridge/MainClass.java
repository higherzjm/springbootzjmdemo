package com.zjm.kindsmodels.bridge;

/*
 *Bridgeģʽ�ֽ����Ž�ģʽ���ǹ����͵���
��ģʽ֮һ��Bridgeģʽ���������С���ԭ��ͨ��
ʹ�÷�װ���ۺ��Լ��̳е���Ϊ���ò�ͬ����е���ͬ
�����Ρ�������Ҫ�ص��ǰѳ���abstraction������Ϊ
ʵ�֣�implementation�����뿪�����Ӷ����Ա��ָ���
�ֵĶ������Լ�Ӧ�����ǵĹ�����չ��
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("�Ž�ģʽ");
		IPeople sad = new Sad();
		Allen allen = new Allen(sad);
		allen.mood();

		IPeople happy = new Happy();
		Alisa alisa = new Alisa(happy);
		alisa.mood();
	}

}
