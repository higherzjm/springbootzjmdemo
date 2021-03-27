package com.zjm.kindsmodels.simplefatory;

/*
 * 简单工厂模式--
 *
 * 简单工厂模式属于类的创建型模式,又叫做静态
 工厂方法模式。通过专门定义一个类来负责创建
 其他类的实例，被创建的实例通常都具有共同的
 父类。
 */
public class MainClass {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		animal cat = Simplefactory.getAnimal("Cat");// 通过工厂类产生新的实例
		cat.eat();
		animal dog = Simplefactory.getAnimal("Dog");
		dog.eat();
	}

}
