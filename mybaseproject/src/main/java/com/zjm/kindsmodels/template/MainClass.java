package com.zjm.kindsmodels.template;

/*
 *   Template Method模式也叫模板方法模式，是
 行为模式之一，它把具有特定步骤算法中的某些
 必要的处理委让给抽象方法，通过子类继承对抽
 象方法的不同实现改变整个算法的行为。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Travel hunanTravel = new HuNanTravel();
		hunanTravel.templateMethod();
		Travel hanzhouTravel = new HanZhouTravel();
		hanzhouTravel.templateMethod();
	}

}
