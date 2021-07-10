package com.zjm.kindsmodels.Memento;
/*
 *  Originator（原生者）
 */
public class People {

	private String name;

	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Memento createMemento() {
		return new Memento(this.name, this.age);
	}

	public void getMemento(Memento memento) {
		this.name = memento.getName();
		this.age = memento.getAge();
	}

	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

}
