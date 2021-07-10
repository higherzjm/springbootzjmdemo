package com.zjm.kindsmodels.Strategy.example1;

/*策略模式
 * Strategy模式也叫策略模式是行为模式之一，
 它对一系列的算法加以封装，为所有算法定义一
 个抽象的算法接口，并通过继承该抽象算法接口
 对所有的算法加以封装和实现，具体的算法选择
 交由客户端决定（策略）。Strategy模式主要用
 来平滑地处理算法的切换
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    Context context=new Context(new Straege1());
    double num=context.cost(800);
    System.out.println(num);
    
	}

}
