package com.zjm.kindsmodels.bridge;

/*
 * ConcreteImplementor
    Implementor子类
 */
public class Allen extends Loving {

	public Allen(IPeople iPeople) {
		super(iPeople);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void mood() {
		System.out.println("allen is "+this.getiPeople().nature());
		
	}

}
