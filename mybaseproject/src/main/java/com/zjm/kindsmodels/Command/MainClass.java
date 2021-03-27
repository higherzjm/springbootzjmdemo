package com.zjm.kindsmodels.Command;


/*
 *  Command模式也叫命令模式 ，是行为设计模
 式的一种。Command模式通过被称为
 Command的类封装了对目标对象的调用行为以
 及调用参数。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
       Peddle peddle=new Peddle();
       Command appleCommand=new AppleCommand(peddle);
       Command bananaCommand=new BananaCommand(peddle);
       
       Waiter waiter=new Waiter();
       waiter.order(appleCommand);
       waiter.order(bananaCommand);
       
      // waiter.remove(appleCommand);
       waiter.sail();
	}
}
