package com.zjm.kindsmodels.Iterator;

public class Book {
	
	private Integer num;
	private String nmae;
	
	
	public Book(Integer num, String nmae) {
		super();
		this.num = num;
		this.nmae = nmae;
	}
	public Integer getNum() {
		return num;
	}
	
	public String getNmae() {
		return nmae;
	}
	public void show() {
		System.out.println(nmae+num);
	}
	

}
