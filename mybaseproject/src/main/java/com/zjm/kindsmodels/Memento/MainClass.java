package com.zjm.kindsmodels.Memento;

/*
 *  MementoģʽҲ�б���¼ģʽ������Ϊģʽ֮
 һ�����������Ǳ��������ڲ�״̬��������Ҫ
 ��ʱ��undo/rollback���ָ�������ǰ��״̬��
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		People people = new People("allen", 24);
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(people.createMemento());// ��������¼
		people.setName("alisa");
		people.setAge(23);
		people.getMemento(caretaker.getMemento());// �ָ�����¼
		System.out.println("name=" + people.getName() + ",age="
				+ people.getAge());

	}

}
