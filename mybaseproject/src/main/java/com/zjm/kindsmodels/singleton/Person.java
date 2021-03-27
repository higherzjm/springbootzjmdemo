package com.zjm.kindsmodels.singleton;



public class Person {
	//final 全局共享变量，值不会变，保持一份 ,第二次创建的覆盖第一次创建的,final变量需要提供初值，否则编译不通过
	private static final Person person=new Person();
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * 私有构造函数不能new创建实例
	 */
	private Person() {
	}

	public static Person getPerson() {

		return person;
	}
}
