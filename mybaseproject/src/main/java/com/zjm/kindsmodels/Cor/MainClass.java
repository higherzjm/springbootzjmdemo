package com.zjm.kindsmodels.Cor;

/*
 * Chain of Responsibility（CoR）模式也叫职
 责链模式或者职责连锁模式，是行为模式之一，
 该模式构造一系列分别担当不同的职责的类的对
 象来共同完成一个任务，这些类的对象之间像链
 条一样紧密相连，所以被称作职责链模式。

 */
public class MainClass {

	/**
	 *            ：系统阅读-》论文阅读-》答辩
	 */
	public static void main(String[] args) {
		Graduate readsys = new ReadSys();
		Graduate readtopic = new ReadTopic();
		Graduate dabian = new DaBian();
		readsys.setGraduate(readtopic).setGraduate(dabian);
		readsys.Do();

	}

}

