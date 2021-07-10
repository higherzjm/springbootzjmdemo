package com.zjm.kindsmodels.decorator;

/*
 * 抽象组件角色： 一个抽象接口，是被装饰类和
 装饰类的父接口。
 */

//当抽象类实现接口时可以不实现所有的方法
public abstract class CarDecorator implements Car {
	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarDecorator(Car car) {
		this.car = car;
	}

	public abstract void show();
}
