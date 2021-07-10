package com.zjm.kindsmodels.Mediator;
/*
 *mediator中介者类的抽象父类。
 */
public abstract class Imediator {
	private Man man;
    private Woman woman;
	public Man getMan() {
		return man;
	}
	public void setMan(Man man) {
		this.man = man;
	}
	public Woman getWoman() {
		return woman;
	}
	public void setWoman(Woman woman) {
		this.woman = woman;
	}
	
    
	public abstract void getMiss(Person person);
	

}
