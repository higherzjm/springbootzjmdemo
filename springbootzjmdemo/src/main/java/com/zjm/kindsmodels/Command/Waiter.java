package com.zjm.kindsmodels.Command;

import java.util.ArrayList;
import java.util.List;

/*
 * Invorker通过Invorker执行Command对象。
 */
public class Waiter {
	private List<Command> commands = new ArrayList<Command>();

	/*
	 * 点单
	 */
	public void order(Command command) {
		commands.add(command);
	}

	/*
	 * 移除订单的某项产品
	 */
	public void remove(Command command) {

		int index=commands.indexOf(command);
		commands.remove(index);//或者 commands.remove(command)也可以

	}

	/*
	 * 购买订单
	 */
	public void sail() {
		for (Command command : commands) {
			command.sail();

		}
	}
}