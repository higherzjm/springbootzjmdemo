package com.zjm.kindsmodels.Strategy.example1;

/*
 *
 * 满100减五十策略
 * 各种策略（算法）的具体实现。
 */
public class Strategy2 extends Strategy {

	@Override
	public double cost(double num) {
		
		if (num>=100) {
			return num-50;
		}
		return num;
	}

}
