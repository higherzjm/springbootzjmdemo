package com.zjm.kindsmodels.Mediator;
/*
 * concreteColleague具体的关联类。
 */
public class Woman extends Person {

	public Woman(String name, int condition, Imediator imediator) {
		super(name, condition, imediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getcompanion(Person person) {
      	    this.getImediator().setWoman(this);
      		this.getImediator().getMiss(person);
	}

}
