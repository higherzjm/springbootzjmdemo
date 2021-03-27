package com.zjm.kindsmodels.state;

/*
 *     State模式也叫状态模式，是行为设计模式的
 一种。State模式允许通过改变对象的内部状态
 而改变对象的行为，这个对象表现得就好像修改
 了它的类一样。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person person = new Person();
		person.setHour(7);
		person.Idosthing();
		person.setHour(12);
		person.Idosthing();
		person.setHour(9);
		person.Idosthing();
		person.setHour(7);
		person.Idosthing();

	}

}
