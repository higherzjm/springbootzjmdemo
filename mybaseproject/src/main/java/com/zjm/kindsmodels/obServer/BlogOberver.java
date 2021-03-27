package com.zjm.kindsmodels.obServer;

import java.util.Observable;
import java.util.Observer;

/*
 * 观察者
 */
public class BlogOberver implements Observer {

	public void update(Observable o, Object arg) {
		Blog blog = (Blog) arg;
		System.out.println("博客已经改变");
		System.out.println("主题：" + blog.getTitle() + ";内容：" + blog.getContent());
	}

}
