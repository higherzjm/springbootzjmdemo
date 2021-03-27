package com.zjm.kindsmodels.bridge;

/*
 * Implementor
    行为实现类接口 (Abstraction接口定义了基于Implementor接口的更高层次的操作)
 */
public abstract class Loving {
	private IPeople iPeople;

	public IPeople getiPeople() {
		return iPeople;
	}

	

	public Loving(IPeople iPeople) {
		super();
		this.iPeople = iPeople;
	}

	 public  abstract void mood();
}
