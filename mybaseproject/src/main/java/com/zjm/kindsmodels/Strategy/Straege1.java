package com.zjm.kindsmodels.Strategy;

/*
 * 达八折策略
 *各种策略（算法）的具体实现。
 *
 */
public class Straege1 extends Strategy {

	@Override
	public double cost(double num) {
		return num*0.8;
	}

}
