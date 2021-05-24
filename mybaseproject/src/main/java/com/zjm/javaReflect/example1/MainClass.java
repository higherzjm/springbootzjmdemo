package com.zjm.javaReflect.example1;

import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

/**
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
            Object object = mainClass.invokeMethod(MyClass1.class, "getName1", "����", 10);
            System.out.println(object);
            Student student = Student.builder().id(100).age(20).name("����").build();
            Object object2 = mainClass.invokeMethod(MyClass2.class, "getStudent2", student);
            System.out.println(object2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @Description: ��ʼ����̬��������Ϣ
     **/
    private void initClassInfo(List<Class> classList) {
        classList.forEach(tClass -> map.put(tClass, tClass.getMethods()));
    }

    /**
     * @Description: ������̬����
     **/
    private <T> Object invokeMethod(Class<T> tClass, String methodName, Object... args) throws Exception {
        Method method = null;
        Object retObject = null;
        if (map.get(tClass) != null) {
            Method[] methods = map.get(tClass);
            for (Method method1 : methods) {
                //�������ƺͲ�������ƥ�䶨λ�����method
                // TODO �ع�������ô���𣬾��Ƕ��ͬ���ķ������ƺͷ������������������Ͳ�һ��
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
