package com.zjm.kindsmodels.Adapter.example1;



/*
 * Adapter模式也叫适配器模式，是构造型模式之一
 ，通过Adapter模式可以改变已有类（或外部类）的接
 口形式。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 继承实现适配器获取狗-----类适配器模
		Adapter1 adapter1 = new Adapter1();
		adapter1.dog();
		// 委让实现适配器获取狗----对象适配器模式
		Adapter2 adapter = new Adapter2(new Animal());
		adapter.dog();
	}

}
