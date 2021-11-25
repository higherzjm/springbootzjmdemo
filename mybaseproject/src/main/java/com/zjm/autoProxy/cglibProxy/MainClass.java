package com.zjm.autoProxy.cglibProxy;

import com.zjm.VO.Student;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;


/**
 * 测试
 * @author zhujianming
 *
 */
public class MainClass {
	public static void main(String[] args) {

	}

	/**
	 * 一个拦截器作为代理对象
	 */
	@Test
	public void oneAsProxyObject(){
		Callback[] callbacks=new Callback[]{new CglibInterceptor1()};
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloWorld.class);
		enhancer.setCallbacks(callbacks);
		//创建代理对象
		Object objectHelper=enhancer.create();
		HelloWorld helloWorld=(HelloWorld) objectHelper;
		helloWorld.saveStudentInfo(Student.builder().name("李四").age(20).build());
	}
	/**
	 * 多个拦截器的代理对象
	 */
	@Test
	public void moreAsProxyObject(){
		Callback[] callbacks=new Callback[]{new CglibInterceptor1(),new CglibInterceptor2()};

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloWorld.class);
		enhancer.setCallbacks(callbacks);
		//设置CallbackFilter，设置多个callback时，必须设置CallbackFilter来确保一个代理类只能接受一个拦截。
		enhancer.setCallbackFilter((method) ->{
			String methodName = method.getName();
			if ("saveStudentInfo".equals(methodName)) {
				return 1; // saveStudentInfo()方法使用callbacks[1]对象拦截。
			}
			return 0;   //其他方法使用callbacks[0]对象拦截。
		});
		//创建代理对象
		Object objectHelper=enhancer.create();
		HelloWorld helloWorld=(HelloWorld) objectHelper;
		helloWorld.saveStudentInfo(Student.builder().name("李四").age(20).build());

		helloWorld.sayHelloWorld("你好");
	}
}
