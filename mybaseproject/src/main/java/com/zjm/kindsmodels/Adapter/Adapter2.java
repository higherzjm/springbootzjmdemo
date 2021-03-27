package com.zjm.kindsmodels.Adapter;


/*
 * 通过委让实现Adapter
 */
public class Adapter2 {
	Animal animal;

	public Adapter2(Animal animal) {
		this.animal = animal;
	}
	
	public void dog() {
        animal.animal1();
	}

}
