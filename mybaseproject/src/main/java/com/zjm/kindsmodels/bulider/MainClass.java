package com.zjm.kindsmodels.bulider;

/*
 * Builder模式也叫建造者模式或者生成器模式，
 是由GoF提出的23种设计模式中的一种。Builder模式是一种对象创建型模式之一，用来
 隐藏复合对象的创建过程，它把复合对象的创建
 过程加以抽象，通过子类继承和重载的方式，动
 态地创建具有复合属性的对象。
 */
public class MainClass {
	public static void main(String[] args) {
		HouseBulider GYbulider = new GongyuBulider();//公寓工程队
		HouseBulider PFbulider = new PingfanBulider();//平房工程队
		new Housedirector(PFbulider);//平房工程师    隐藏复合对象的创建过程
		new Housedirector(GYbulider);//公寓工程师    隐藏复合对象的创建过程
		House house = PFbulider.getHouse();
		House house2 = GYbulider.getHouse();
		System.out.println(house.getFloor());
		System.out.println(house.getWall());
		System.out.println(house2.getFloor());
		System.out.println(house2.getWall());

	}
}

