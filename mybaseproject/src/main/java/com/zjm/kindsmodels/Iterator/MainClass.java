package com.zjm.kindsmodels.Iterator;

/*
 *   Iterator模式也叫迭代模式，是行为模式之
 一，它把对容器中包含的内部对象的访问委让给
 外部类，使用Iterator（遍历）按顺序进行遍历
 访问的设计模式。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BookList bookList = new BookList();
		Book book1 = new Book(88, "allen");
		Book book2 = new Book(99, "Alisa");
		bookList.addzbook(book1);
		bookList.addzbook(book2);

		while (bookList.hasnext()) {
			Book book = bookList.getNext();
			book.show();

		}

	}
}
