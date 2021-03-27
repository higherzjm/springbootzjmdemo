package com.zjm.kindsmodels.Cor;
/*
 * concreteHandler具体的处理类。
 */
public class ReadTopic extends Graduate {


	@Override
	public void Do() {
		System.out.println("阅读论文");
		if (this.getGraduate()!=null) {
			this.graduate.Do();
		}
	}

}
