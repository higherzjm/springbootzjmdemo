package com.zjm.kindsmodels.composite;

import java.util.List;

public class File implements Ifile {
	private String name;

	public File(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public boolean add(Ifile file) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(Ifile file) {
		// TODO Auto-generated method stub
		return false;
	}

	public void display() {
       System.out.println("file:"+name);
       
	}

	public List<Ifile> getChild() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Ifile> getChild2() {
		// TODO Auto-generated method stub
		return null;
	}

}
