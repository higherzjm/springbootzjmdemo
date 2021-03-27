package com.zjm.kindsmodels.decorator;
/*
 * 具体组件角色：为抽象组件的实现类。
 */
public class RunCar implements Car {

	public void run() {
		System.out.println("RunCar");
	}

	public void show() {
		this.run();
	}

}
