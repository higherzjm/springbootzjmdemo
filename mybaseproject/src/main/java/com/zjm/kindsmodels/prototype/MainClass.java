package com.zjm.kindsmodels.prototype;

/*
 原型模式
 * Prototype模式是一种对象创建型模式，它采
取复制原型对象的方法来创建对象的实例。使用
Prototype模式创建的实例，具有与原型一样的
数据。
。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person person = new Person();
		person.setName("allen");
		person.setAge(24);
		Person person2 = person.clone();//第二次克隆第一次的
		person2.setAge(25);
		System.out.println(person.getName()+""+person.getAge());
		System.out.println(person2.getName()+""+person2.getAge());//第二次创建的，如果值没有手动改变，则结果为第一次创建的
		System.out.println(person == person2);//原型模式每次创建的不一样，返回false

	}

}
