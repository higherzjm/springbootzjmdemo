package com.zjm.kindsmodels.facade;

/*
 *  功能提供者。指提供功能的类群（模块或子系统）
 */
public class FacadeAnimal {

	private Cat cat;
	private Dog dog;

	public FacadeAnimal() {
		cat = new Cat();
		dog = new Dog();
	}

	public void buy() {
		this.cat.buy();
		this.dog.buy();
		//this.buy();
	}
}
