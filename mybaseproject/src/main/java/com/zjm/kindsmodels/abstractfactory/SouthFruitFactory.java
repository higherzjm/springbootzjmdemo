package com.zjm.kindsmodels.abstractfactory;

public class SouthFruitFactory implements FruitFactory {


	public Fruit getBanana() {
		return new SouthBanana();
	}

	public Fruit getorange() {
		// TODO Auto-generated method stub
		return null;
	}

}
