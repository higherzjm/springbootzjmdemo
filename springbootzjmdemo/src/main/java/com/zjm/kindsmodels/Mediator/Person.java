package com.zjm.kindsmodels.Mediator;
/*
 * colleague关联类的抽象父类。
 */
public abstract class Person {

	private String name;
	private int condition;
	private Imediator imediator;

	public Imediator getImediator() {
		return imediator;
	}

	public void setImediator(Imediator imediator) {
		this.imediator = imediator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public Person(String name, int condition, Imediator imediator) {
		this.name = name;
		this.condition = condition;
		this.imediator = imediator;
	}

	public abstract void getcompanion(Person person);

}
