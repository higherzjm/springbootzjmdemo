package com.zjm.kindsmodels.factoryfunction;

/*
工厂方法模式---
 *
 * 工厂方法模式同样属于类的创建型模式又被称
为多态工厂模式 。工厂方法模式的意义是定义一个创建
产品对象的工厂接口，将实际创建工作推迟到子类当中。
核心工厂类不再负责产品的创建，这样核心类成为一个
抽象工厂角色，仅负责具体工厂子类必须实现的接口，
这样进一步抽象化的好处是使得工厂方法模式可以使系
统在不修改具体工厂角色的情况下引进新的产品
 */
public class MainClass {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args){
		AnimalFactory catfactory=new CatFactory();
		Animal cat=catfactory.getanmAnimal();
		cat.eat();
	}

}
