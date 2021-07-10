package com.zjm.kindsmodels.Interpreter;

import java.util.ArrayList;
import java.util.List;

/*
 * interpreter模式也叫解释器模式，是行为模式之一，它
 是一种特殊的设计模式，它建立一个解释器，对于特定
 的计算机程序设计语言，用来解释预先定义的文法。简
 单地说，Interpreter模式是一种简单的语法解释器构架。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String num = "20";
		Context context = new Context(num);
		List<Express> expresses = new ArrayList<Express>();
		PlusExpress plusExpress = new PlusExpress(context);
		SubExpress subExpress = new SubExpress(context);

		expresses.add(plusExpress);
		expresses.add(subExpress);
		for (Express express : expresses) {
			 express.Interpreter();
			 System.out.println(context.getOutput());
		}

	}

}
