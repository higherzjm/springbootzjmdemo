package com.zjm.kindsmodels.bulider;

/*
 *ƽ�����̶�
 */
public class PingfanBulider implements HouseBulider {

	House house = new House();

	public void buliderfloor() {
		house.setFloor("ƽ��floor");
	}

	public void buliderwall() {
		house.setWall("ƽ��wall");
	}

	public House getHouse() {
		return house;
	}

}
