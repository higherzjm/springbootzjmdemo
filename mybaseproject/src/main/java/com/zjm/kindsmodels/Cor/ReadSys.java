package com.zjm.kindsmodels.Cor;
/*
 * concreteHandler具体的处理类。
 */
public class ReadSys extends Graduate {

	@Override
	public void Do() {
		System.out.println("阅读系统");
		if (this.getGraduate()!=null) {
			this.getGraduate().Do();
		}
	}
}

