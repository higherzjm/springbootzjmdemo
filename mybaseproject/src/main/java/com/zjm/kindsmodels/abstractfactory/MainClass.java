package com.zjm.kindsmodels.abstractfactory;

/*
 *  抽象工厂模式是所有形态的工厂模式中最为抽
 象和最其一般性的。抽象工厂模式可以向客户端
 提供一个接口，使得客户端在不必指定产品的具
 体类型的情况下，能够创建多个产品族的产品对
 象。
 */
public class MainClass {
	public static void main(String[] args) {
		FruitFactory ff = new NorthFruitFactory();
		Fruit banana = ff.getBanana();
		banana.get();

		Orange orange = new NorthOrange();
		orange.get();

		FruitFactory ff2 =new SouthFruitFactory();

		Fruit banana2 = ff2.getBanana();
		banana2.get();

	}
}
