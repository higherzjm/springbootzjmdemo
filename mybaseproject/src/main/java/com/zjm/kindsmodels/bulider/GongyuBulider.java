package com.zjm.kindsmodels.bulider;

/*
公寓工程队
* */
public class GongyuBulider implements HouseBulider {

	House house = new House();
	public void buliderfloor() {
		house.setFloor("公寓 floor");
	}

	public void buliderwall() {
		house.setWall("公寓wall");
	}

	public House getHouse() {
		return house;
	}


}
