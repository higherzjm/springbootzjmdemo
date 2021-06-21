package com.zjm.kindsmodels.Cor;


/*
 * Handler 处理类的抽象父类。
 */
public abstract class Graduate {
	public abstract void Do();

	protected Graduate graduate;//子类可以访问 ，用private修饰符子类不能访问

	public Graduate getGraduate() {
		return graduate;
	}

	public Graduate setGraduate(Graduate graduate) {
		this.graduate = graduate;
		return this.graduate;
	}


}
