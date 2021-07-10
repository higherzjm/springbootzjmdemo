package com.zjm.kindsmodels.state;

public class Nostate extends Istate {

	@Override
	public void doSthing(Person person) {
         System.out.println(person.getHour()+"未定义");
	}

}
