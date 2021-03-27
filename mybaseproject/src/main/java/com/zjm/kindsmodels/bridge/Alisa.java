package com.zjm.kindsmodels.bridge;

/*
* Client
    Bridge模式的使用者

ConcreteImplementor
    Implementor子类
 */
public class Alisa extends Loving {

	public Alisa(IPeople iPeople) {
		super(iPeople);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mood() {
		System.out.println("alisa is "+this.getiPeople().nature());

	}

}
