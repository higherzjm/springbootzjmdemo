package com.zjm.kindsmodels.Cor;

/*
 * concreteHandler具体的处理类。
 */
public class DaBian extends Graduate {

	@Override
	public void Do() {
		System.out.println("答辩");
		if (this.getGraduate()!=null) {
			this.getGraduate().Do();
		}
	}

}
