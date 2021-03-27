package com.zjm.kindsmodels.Mediator;

/*
 *Mediator模式也叫中介者模式，是由GoF提出的23种
 软件设计模式的一种。Mediator模式是行为模式之一，
 在Mediator模式中，类之间的交互行为被统一放在
 Mediator的对象中，对象通过Mediator对象同其他对象
 交互，Mediator对象起着控制器的作用。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Imediator imediator = new Mediator();
		Person allen = new Man("zjm", 20, imediator);
		Person alisa = new Woman("fxy", 20, imediator);
		allen.getcompanion(alisa);

	}

}
