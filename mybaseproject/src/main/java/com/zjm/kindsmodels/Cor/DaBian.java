package com.zjm.kindsmodels.Cor;

/*
 * concreteHandler����Ĵ����ࡣ
 */
public class DaBian extends Graduate {

	@Override
	public void Do() {
		System.out.println("���");
		if (this.getGraduate()!=null) {
			this.getGraduate().Do();
		}
	}

}
