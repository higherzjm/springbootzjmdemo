package com.zjm.kindsmodels.Mediator;

/*
 * concreteColleague具体的关联类。
 */
public class Man extends Person {

	public Man(String name, int condition, Imediator imediator) {
		super(name, condition, imediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getcompanion(Person person) {
		this.getImediator().setMan(this);//加入man本事
		this.getImediator().getMiss(person);//寻找woman对象
	}

}
