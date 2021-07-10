package com.zjm.kindsmodels.singleton;

/*
 *单例模式
 * 是一种对象创建型模式，使用单例模式，
 可以保证为一个类只生成唯一的实例对象。也就是说，
 在整个程序空间中，该类只存在一个实例对象。
 其实，GoF对单例模式的定义是：保证一个类、
 只有一个实例存在，同时提供能对该实例加以访
 问的全局访问方法。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Person2 person1 = Person2.getPerson();
		person1.setName("aa");
		Person2 person2 = Person2.getPerson();
		person2.setName("bb");
		System.out.println(person2.getName());
		System.out.println(person1.getName());
		System.out.println(person1==person2);
		
		
		Person person3 = Person.getPerson();
		Person person4 = Person.getPerson();
		Person person5 = Person.getPerson();
		person3.setName("cc");
		person4.setName("dd");
		person5.setName("ff");
		System.out.println(person3.getName());
		System.out.println(person4.getName());
		System.out.println(person3==person4);

	}

}
