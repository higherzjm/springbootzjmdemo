package com.zjm.kindsmodels.Cor;
/*
 * concreteHandler����Ĵ����ࡣ
 */
public class ReadSys extends Graduate {

	@Override
	public void Do() {
		System.out.println("�Ķ�ϵͳ");
		if (this.getGraduate()!=null) {
			this.getGraduate().Do();
		}
	}
}

