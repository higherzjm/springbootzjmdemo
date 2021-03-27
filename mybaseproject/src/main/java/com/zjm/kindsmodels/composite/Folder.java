package com.zjm.kindsmodels.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder implements Ifile {

	private String name;
	private int num;
	private List<Ifile> children;
	private List<Ifile> children2;

	public Folder(String name) {
		this.name = name;
		children = new ArrayList<Ifile>();
	}

	public Folder(String name, int num) {
		this.name = name;
		this.num = num;
		children2 = new ArrayList<Ifile>();

	}

	public boolean add(Ifile file) {
		// TODO Auto-generated method stub
		return children.add(file);
	}
	
	public boolean add2(Ifile file) {
		// TODO Auto-generated method stub
		return children2.add(file);
	}

	public boolean delete(Ifile file) {
		// TODO Auto-generated method stub
		return children.remove(file);
	}

	public void display() {
		System.out.println(name);
	}

	public List<Ifile> getChild() {
		// TODO Auto-generated method stub
		return children;
	}
	public List<Ifile> getChild2() {
		// TODO Auto-generated method stub
		return children2;
	}

}
