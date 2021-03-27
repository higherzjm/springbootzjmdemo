package com.zjm.kindsmodels.state;

/*
 * ConcreteState：接口实现类或子类
 实现了一个与Context某个状态相关的行为。
 */
public class MorinState extends Istate {

	@Override
	public void doSthing(Person person) {
		if (person.getHour() == 7) {
			System.out.println("早餐");
		} else {
			person.setIstate(new NoonState());
			person.Idosthing();
		}
	}

}
