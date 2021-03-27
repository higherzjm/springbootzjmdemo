package com.zjm.kindsmodels.Visitor;

/*
 *  Visitor模式也叫访问者模式，是行为模式之一
 ，它分离对象的数据和行为，使用Visitor模式，
 可以不修改已有类的情况下，增加新的操作。
 */
public class MainClass {
	public static void main(String[] args) {
		Park park = new Park();
		park.setName("越秀公园");
		VisitorA visitorA = new VisitorA();
		park.accept(visitorA);

		VisitorB visitorB = new VisitorB();
		park.accept(visitorB);

		VisitorManager visitorManager = new VisitorManager();
		park.accept(visitorManager);
	}
}
