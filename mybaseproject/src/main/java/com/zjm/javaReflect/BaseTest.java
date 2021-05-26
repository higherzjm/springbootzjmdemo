package com.zjm.javaReflect;

import com.zjm.Enum.ParamTyeEnum;
import com.zjm.VO.Student;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    private static Class<?> clazz;

    {
        try {
            clazz = Class.forName("com.zjm.javaReflect.BaseTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void test1() {
        try {
            Method method = clazz.getMethod("getStudentInfo", Student.class);
            Student student = Student.builder().id(10).name("张三").age(20).build();
            Object obj = method.invoke(clazz.newInstance(), student);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try {
            Method method = clazz.getMethod("getName", String.class, String.class, Integer.class);
            Object obj = method.invoke(clazz.newInstance(), "李梅", "上海", 21);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void test3(Class<T> clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("getName".equals(method.getName()) || "getStudentInfo".equals(method.getName())) {
                System.out.println("方法名称:" + method.getName());
                Parameter[] parameters = method.getParameters();
                System.out.println("参数数量:" + parameters.length);
                for (Parameter parameter : parameters) {
                    System.out.println("参数名称:" + parameter.getName());
                }
                Class[] parameterTypes = method.getParameterTypes();
                System.out.println("参数类型数量:" + parameterTypes.length);
                List<Object> paramValue = new ArrayList<>();
                int i=0;
                for (Class parameterType : parameterTypes) {
                    System.out.println("参数类型:" + parameterType.getName());
                    ParamTyeEnum paramTyeEnum=ParamTyeEnum.paramTyeEnumValueOf(parameterType.getName());
                    if (paramTyeEnum!=null) {
                        switch (paramTyeEnum) {
                            case String:
                                paramValue.add("我是中国人"+(i++));
                                break;
                            case Integer:
                                paramValue.add(100 + (i++));
                                break;
                            default:
                                paramValue.add(parameterType.newInstance());
                        }
                    }else {
                        paramValue.add(parameterType.newInstance());
                    }
                }
                Object obj=method.invoke(clazz.newInstance(), paramValue.toArray());
                System.out.println("invoke ret:"+obj);
                System.out.println("----------------------------");
            }

        }

    }

    public static void main(String[] args) throws Exception {
        BaseTest test1 = new BaseTest();
        //test1.test1();
        //test1.test2();
        test1.test3(clazz);
    }

    public String getName(String name, String address, Integer age) {
        return name + "是一名人民教师,在" + address + "," + age + "岁";
    }

    public Student getStudentInfo(Student student) {
        return student;
    }
}
