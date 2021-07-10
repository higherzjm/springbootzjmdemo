package com.zjm.kindsmodels.template;
/*
 旅行具有统一的操作步骤或操作过程:先买票后上车
 *
 * 抽象类的父类
 */
public abstract class Travel {

	public abstract void buyTicket();//买票

	public abstract void onbus();//上车

	public void templateMethod() {//模板方法
		this.buyTicket();//掉用子类的方法
		this.onbus();
	}
}
