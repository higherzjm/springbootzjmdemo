package com.zjm.kindsmodels.Visitor;
/*
�����߽�ɫ��Visitor��
 */
public interface Visitor {
	public void visit(Park park);
	public void visit(ParkA parkA);
	public void visit(ParkB parkB);
}
