package com.zjm.kindsmodels.factoryfunction;


public class CatFactory implements AnimalFactory {

	public Animal getanmAnimal() {
		return new Cat();
	}

}
