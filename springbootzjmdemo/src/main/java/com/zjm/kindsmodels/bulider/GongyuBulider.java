package com.zjm.kindsmodels.bulider;

/*
��Ԣ���̶�
* */
public class GongyuBulider implements HouseBulider {

	House house = new House();
	public void buliderfloor() {
		house.setFloor("��Ԣ floor");
	}

	public void buliderwall() {
		house.setWall("��Ԣwall");
	}

	public House getHouse() {
		return house;
	}


}
