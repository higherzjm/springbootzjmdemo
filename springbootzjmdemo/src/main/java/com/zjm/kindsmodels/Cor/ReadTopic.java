package com.zjm.kindsmodels.Cor;
/*
 * concreteHandler����Ĵ����ࡣ
 */
public class ReadTopic extends Graduate {


	@Override
	public void Do() {
		System.out.println("�Ķ�����");
		if (this.getGraduate()!=null) {
			this.graduate.Do();
		}
	}

}
