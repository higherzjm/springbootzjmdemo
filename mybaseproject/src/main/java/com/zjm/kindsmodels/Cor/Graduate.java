package com.zjm.kindsmodels.Cor;


/*
 * Handler ������ĳ����ࡣ
 */
public abstract class Graduate {
	public abstract void Do();

	protected Graduate graduate;//������Է��� ����private���η����಻�ܷ���

	public Graduate getGraduate() {
		return graduate;
	}

	public Graduate setGraduate(Graduate graduate) {
		this.graduate = graduate;
		return this.graduate;
	}


}
