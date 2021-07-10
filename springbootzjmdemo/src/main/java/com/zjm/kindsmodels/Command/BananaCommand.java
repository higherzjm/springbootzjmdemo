package com.zjm.kindsmodels.Command;
/*
 *  ConcreteCommand Command的具体实现类。
 */
public class BananaCommand extends Command {

	public BananaCommand(Peddle peddle) {
		super(peddle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sail() {
       this.getPeddle().sailBanana();
	}

}
