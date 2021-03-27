package com.zjm.kindsmodels.facade;

public class MainClass {

	/**
	 * Facade模式也叫外观模式，是由GoF提出的 23种设计模式中的一种。
	 * Facade模式为一组具 有类似功能的类群，比如类库，子系统等等，提
	 * 供一个一致的简单的界面。这个一致的简单的界 面被称作facade。
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FacadeAnimal facadeAnimal = new FacadeAnimal();
		facadeAnimal.buy();//通过外观模式功能提供者同时buy cat 和 buy dog
	}

}
