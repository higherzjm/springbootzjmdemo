package com.zjm.autoProxy.cglibProxy;

import com.zjm.VO.Student;

/**
 * ����
 * @author sunlh
 *
 */
public class MainClass {
	public static void main(String[] args) {
		//��ǰ����Ϊ�������
		CglibMethodInterceptor1 cglibMethodInterceptor1 = new CglibMethodInterceptor1();
		HelloWorld proxy = (HelloWorld) cglibMethodInterceptor1.getProxy(HelloWorld.class);
		proxy.sayHelloWorld("����");

		//��������Ϊ�������
		CglibMethodInterceptor2 cglibMethodInterceptor2=new CglibMethodInterceptor2(cglibMethodInterceptor1);
		HelloWorld proxy2 = (HelloWorld) cglibMethodInterceptor2.getProxy2(HelloWorld.class);
		proxy2.saveStudentInfo(Student.builder().name("����").age(20).build());

	}
}
