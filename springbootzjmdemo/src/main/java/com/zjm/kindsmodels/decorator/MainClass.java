package com.zjm.kindsmodels.decorator;

/*
 *  装饰（ Decorator ）模式又叫做包装模式。通
 过一种对客户端透明的方式来扩展对象的功能，
 是继承关系的一个替换方案。
 */
public class MainClass {
	public static void main(String[] args) {
		Car car = new RunCar();

		car.show();
		System.out.println("---------");

		Car swimCar = new SwimCarDecorator(car);
		swimCar.show();
		System.out.println("---------");

		Car flySwimCar = new FlyCarDecorator(swimCar);
		flySwimCar.show();
	}
}
