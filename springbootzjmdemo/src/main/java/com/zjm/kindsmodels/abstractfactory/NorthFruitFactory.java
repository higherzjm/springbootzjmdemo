package com.zjm.kindsmodels.abstractfactory;

public class NorthFruitFactory implements FruitFactory {


	public Fruit getBanana() {
		return new NorthBanana();
	}

	public Fruit getorange() {
		return new NorthOrange();
	}

}
