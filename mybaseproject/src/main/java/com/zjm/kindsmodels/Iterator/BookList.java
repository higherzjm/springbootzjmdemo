package com.zjm.kindsmodels.Iterator;

import java.util.ArrayList;
import java.util.List;

/*
 * 1.由容器自己实现顺序遍历。直接在容器类里直接添加顺序遍历方法
 */
public class BookList {

	List<Book> books;
	int index = 0;

	public BookList() {
		books = new ArrayList<Book>();
	}

	public void addzbook(Book book) {
		books.add(book);
		
	}

	public void removebook(Book book) {
		Integer inbook = books.indexOf(book);
		books.remove(inbook);
	}

	public boolean hasnext() {
		if (index >= books.size()) {
			System.out.println("end");
			return false;
		} else {
			return true;
		}
	}

	public Book getNext() {
		return books.get(index++);
    
   }
	
	
	

}
