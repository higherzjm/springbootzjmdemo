package com.zjm.kindsmodels.obServer;

/*观察者模式
 * Observer模式是行为模式之一，它的作用是当
 一个对象的状态发生变化时，能够自动通知其他
 关联对象，自动刷新对象状态。
 Observer模式提供给关联对象一种同步通信的
 手段，使某个对象与依赖它的其他对象之间保持
 状态同步。
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlogByObserver blogByObserver = new BlogByObserver();//实例化被观察者
		blogByObserver.addObserver(new BlogOberver());// 注册观察者
		Blog blog = new Blog();
		blog.setTitle("主题");
		blog.setContent("内容");

		blogByObserver.blog(blog);//注入观察对象
		

	}

}
