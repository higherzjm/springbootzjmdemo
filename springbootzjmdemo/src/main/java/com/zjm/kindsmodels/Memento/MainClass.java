package com.zjm.kindsmodels.Memento;

/*
 *  Memento模式也叫备忘录模式，是行为模式之
 一，它的作用是保存对象的内部状态，并在需要
 的时候（undo/rollback）恢复对象以前的状态。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		People people = new People("allen", 24);
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(people.createMemento());// 创建备忘录
		people.setName("alisa");
		people.setAge(23);
		people.getMemento(caretaker.getMemento());// 恢复备忘录
		System.out.println("name=" + people.getName() + ",age="
				+ people.getAge());

	}

}
