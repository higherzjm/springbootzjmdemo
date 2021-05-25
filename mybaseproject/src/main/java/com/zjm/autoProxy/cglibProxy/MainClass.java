package com.zjm.autoProxy.cglibProxy;

import com.zjm.VO.Student;

/**
 * 测试
 * @author sunlh
 *
 */
public class MainClass {
	public static void main(String[] args) {
		//当前对象为代理对象
		CglibMethodInterceptor1 cglibMethodInterceptor1 = new CglibMethodInterceptor1();
		HelloWorld proxy = (HelloWorld) cglibMethodInterceptor1.getProxy(HelloWorld.class);
		proxy.sayHelloWorld("张三");

		//其他对象为代理对象
		CglibMethodInterceptor2 cglibMethodInterceptor2=new CglibMethodInterceptor2(cglibMethodInterceptor1);
		HelloWorld proxy2 = (HelloWorld) cglibMethodInterceptor2.getProxy2(HelloWorld.class);
		proxy2.saveStudentInfo(Student.builder().name("李四").age(20).build());

	}
}
