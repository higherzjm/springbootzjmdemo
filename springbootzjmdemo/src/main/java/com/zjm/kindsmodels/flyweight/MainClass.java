package com.zjm.kindsmodels.flyweight;

/*
Flyweight模式也叫享元模式，是构造型模式之
 一，它通过与其他类似对象共享数据来减小内存
 占用。

 */
public class MainClass {
	public static void main(String[] args) {
		TeacherFactory factory = new TeacherFactory();
		Teacher teacher1 = factory.getTeacher("0102034");
		Teacher teacher2 = factory.getTeacher("0102035");
		Teacher teacher3 = factory.getTeacher("0102034");
		Teacher teacher4 = factory.getTeacher("0102037");

		System.out.println(teacher1.getNumber());
		System.out.println(teacher2.getNumber());
		System.out.println(teacher3.getNumber());
		System.out.println(teacher4.getNumber());

		if (teacher1 == teacher3) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
