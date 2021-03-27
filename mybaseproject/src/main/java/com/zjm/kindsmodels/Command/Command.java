package com.zjm.kindsmodels.Command;
/*
 * CommandCommand抽象类。
 */
public abstract class Command {
	private Peddle peddle;

	public Peddle getPeddle() {
		return peddle;
	}

	public void setPeddle(Peddle peddle) {
		this.peddle = peddle;
	}

	public Command(Peddle peddle) {
		super();
		this.peddle = peddle;
	}

	public abstract void sail();

}
