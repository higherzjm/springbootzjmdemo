package com.zjm.kindsmodels.flyweight;

import java.util.HashMap;
import java.util.Map;


/*
 * 享元工厂
 *
 * 享元工厂获得的享元对象可减少只用内存，因素共享
 */
public class TeacherFactory {
     private Map<String, Teacher> teachers;

	public TeacherFactory() {
		teachers=new HashMap<String, Teacher>();
		// TODO Auto-generated constructor stub
	}
	public Teacher getTeacher(String number) {
		Teacher teacher = teachers.get(number);
		if(teacher == null) {
			System.out.println("coming");
			teacher = new Teacher();
			teacher.setNumber(number);
			teachers.put(number, teacher);
		}
		return teacher;
	}
     
     
}
