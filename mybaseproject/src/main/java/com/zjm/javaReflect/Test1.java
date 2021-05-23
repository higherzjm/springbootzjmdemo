package com.zjm.javaReflect;

import com.zjm.Enum.ParamTyeEnum;
import com.zjm.VO.Student;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Test1 {
    private static Class<?> clazz;

    {
        try {
            clazz = Class.forName("com.zjm.javaReflect.Test1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void test1() {
        try {
            Method method = clazz.getMethod("getStudentInfo", Student.class);
            Student student = Student.builder().id(10).name("����").age(20).build();
            Object obj = method.invoke(clazz.newInstance(), student);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try {
            Method method = clazz.getMethod("getName", String.class, String.class, Integer.class);
            Object obj = method.invoke(clazz.newInstance(), "��÷", "�Ϻ�", 21);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void test3(Class<T> clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("getName".equals(method.getName()) || "getStudentInfo".equals(method.getName())) {
                System.out.println("��������:" + method.getName());
                Parameter[] parameters = method.getParameters();
                System.out.println("��������:" + parameters.length);
                for (Parameter parameter : parameters) {
                    System.out.println("��������:" + parameter.getName());
                }
                Class[] parameterTypes = method.getParameterTypes();
                System.out.println("������������:" + parameterTypes.length);
                List<Object> paramValue = new ArrayList<>();
                int i=0;
                for (Class parameterType : parameterTypes) {
                    System.out.println("��������:" + parameterType.getName());
                    ParamTyeEnum paramTyeEnum=ParamTyeEnum.paramTyeEnumValueOf(parameterType.getName());
                    if (paramTyeEnum!=null) {
                        switch (paramTyeEnum) {
                            case String:
                                paramValue.add("�����й���"+(i++));
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
        Test1 test1 = new Test1();
        //test1.test1();
        //test1.test2();
        test1.test3(clazz);
    }

    public String getName(String name, String address, Integer age) {
        return name + "��һ�������ʦ,��" + address + "," + age + "��";
    }

    public Student getStudentInfo(Student student) {
        return student;
    }
}
