package com.zjm.kindsmodels.singleton;

public class Person2 {
	private static Person2 person;
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
	private Person2() {
	}

	// 线程使用全局同步，避免多线程实例化两个对象
	/*
	 * 局部同步和全局同步根据情况可以选择其一
	 */
	public static synchronized Person2 getPerson() {
		if (person == null) {
			synchronized (Person2.class) {// 局部同步
				if (person == null) {// 双重检查
					person = new Person2();
				}

			}

		}
		return person;
	}

}
