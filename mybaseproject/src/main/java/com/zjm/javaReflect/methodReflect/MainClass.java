package com.zjm.javaReflect.methodReflect;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 方法反射调用
 * @author zhujianming
 */
@Slf4j
public class MainClass {
    private Map<Class, Method[]> map = new HashMap<>();

    public static void main(String[] args) {

        MainClass mainClass = new MainClass();

        List<Class> classList = Arrays.asList(MyClass1.class, MyClass2.class);
        mainClass.initClassInfo(classList);
        try {
            Object object = mainClass.invokeMethod(MyClass1.class, "getName1", "张三", 10);
            System.out.println(object);
            Student student = Student.builder().id(100).age(20).name("李四").build();
            Object object2 = mainClass.invokeMethod(MyClass2.class, "getStudent2", student);
            System.out.println(object2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @Description: 初始化动态代理类信息
     **/
    private void initClassInfo(List<Class> classList) {
        classList.forEach(tClass -> map.put(tClass, tClass.getMethods()));
    }

    /**
     * @Description: 方法动态代理
     **/
    private <T> Object invokeMethod(Class<T> tClass, String methodName, Object... args) throws Exception {
        Method method = null;
        Object retObject = null;
        if (map.get(tClass) != null) {
            Method[] methods = map.get(tClass);
            for (Method method1 : methods) {
                //方法名称和参数个数匹配定位具体的method
                // TODO 重构函数怎么区别，就是多个同样的方法名称和方法个数，但方法类型不一样
                if (method1.getName().equals(methodName) && method1.getParameters().length == args.length) {
                    method = method1;
                }
            }

            if (method != null) {
                retObject = method.invoke(tClass.newInstance(), args);
            }
        }
        return retObject;
    }
}
