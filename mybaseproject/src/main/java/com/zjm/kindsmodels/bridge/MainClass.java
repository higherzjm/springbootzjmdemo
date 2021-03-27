package com.zjm.kindsmodels.bridge;

/*
 *Bridge模式又叫做桥接模式，是构造型的设
计模式之一。Bridge模式基于类的最小设计原则，通过
使用封装，聚合以及继承等行为来让不同的类承担不同
的责任。它的主要特点是把抽象（abstraction）与行为
实现（implementation）分离开来，从而可以保持各部
分的独立性以及应对它们的功能扩展。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPeople sad = new Sad();
		Allen allen = new Allen(sad);
		allen.mood();

		IPeople happy = new Happy();
		Alisa alisa = new Alisa(happy);
		alisa.mood();
	}

}
