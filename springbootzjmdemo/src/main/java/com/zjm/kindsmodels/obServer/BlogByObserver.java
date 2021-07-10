package com.zjm.kindsmodels.obServer;

import java.util.Observable;

/*
 * 被观察者
 */
public class BlogByObserver  extends Observable {
  public void blog(Blog blog) {
	  this.setChanged();// 指示已經改變,否则无法确定是否改变
	  this.notifyObservers(blog);//通知观察者，并且传递被观察对象对象
	  
	
}
}
