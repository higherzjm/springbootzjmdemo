package com.zjm.kindsmodels.simplefatory;


//简单工厂模式
public class Simplefactory {


	public static animal getAnimal(String type) throws InstantiationException,
			IllegalAccessException {
		if (type.equalsIgnoreCase("dog")) {
			return Dog.class.newInstance();// 通过传递任何参数判断创建新的实例
		} else if (type.equalsIgnoreCase("cat")) {
			return Cat.class.newInstance();
		} else {
			System.out.println("没有找到实例");
			return null;
		}
    	/*Class  animal = Class.forName(type);// 同樣通过传递的参数创建实例，但这个参数必须是类名
		return (animal) animal.newInstance();*/
	}


}
