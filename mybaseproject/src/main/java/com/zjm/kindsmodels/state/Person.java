package com.zjm.kindsmodels.state;

/*
 * Context：用户对象
 拥有一个State类型的成员，以标识对象的当前
 状态；
 */
public class Person {

	private Integer hour;
	private Istate istate;

	public Person() {
		super();
		this.istate = new MorinState();
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public void Idosthing() {
		istate.doSthing(this);
		// 复位，都所以方法以后再执行。
		istate = new MorinState();
	}

	public Istate getIstate() {
		return istate;
	}

	public void setIstate(Istate istate) {
		this.istate = istate;
	}
}
