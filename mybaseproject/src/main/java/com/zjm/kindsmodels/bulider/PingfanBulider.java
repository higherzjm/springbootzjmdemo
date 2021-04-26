package com.zjm.kindsmodels.bulider;

/*
 *平方工程队
 */
public class PingfanBulider implements HouseBulider {

	House house = new House();

	public void buliderfloor() {
		house.setFloor("平房floor");
	}

	public void buliderwall() {
		house.setWall("平房wall");
	}

	public House getHouse() {
		return house;
	}

}
