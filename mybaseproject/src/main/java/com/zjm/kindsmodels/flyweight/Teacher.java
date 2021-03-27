package com.zjm.kindsmodels.flyweight;

public class Teacher extends Person {

	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Teacher(String name, int age, String number) {
		super(name, age);//继承
		this.number = number;
		// TODO Auto-generated constructor stub
	}

	public Teacher() {
		super();//继承
	}


}
