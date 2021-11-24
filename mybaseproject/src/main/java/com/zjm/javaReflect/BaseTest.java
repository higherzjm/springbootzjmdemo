package com.zjm.javaReflect;

import com.zjm.Enum.ParamTyeEnum;
import com.zjm.VO.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseTest {
    private static Class<?> clazz;

    {
        try {
            clazz = Class.forName("com.zjm.javaReflect.BaseTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类获取指定方法名且指定参数类型的方法
     * 并动态方法调用
     */
    @Test
    public void invokeMethodSignParam() {
        try {
            Method method = clazz.getMethod("getStudentInfo", Student.class);
            Student student = Student.builder().id(10).name("张三").age(20).build();
            Object obj = method.invoke(clazz.newInstance(), student);
            log.info("返回结果:"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 通过类获取指定方法名且指定参数类型的方法（多参数）
     * 并动态方法调用
     */
    @Test
    public void invokeMethodMoreParam() {
        try {
            Method method = clazz.getMethod("getName", String.class, String.class, Integer.class);
            Object obj = method.invoke(clazz.newInstance(), "李梅", "上海", 21);
            log.info("返回结果:"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态方法参数类型
     * @throws Exception
     */
    @Test
    public void dyMethodParam() throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if ("getName".equals(method.getName()) || "getStudentInfo".equals(method.getName())) {
                log.info("方法名称:" + method.getName());
                Parameter[] parameters = method.getParameters();
                log.info("参数数量:" + parameters.length);
                for (Parameter parameter : parameters) {
                    log.info("参数名称:" + parameter.getName());
                }
                Class[] parameterTypes = method.getParameterTypes();
                log.info("参数类型数量:" + parameterTypes.length);
                List<Object> paramValue = new ArrayList<>();
                int i=0;
                for (Class parameterType : parameterTypes) {
                    log.info("参数类型:" + parameterType.getName());
                    ParamTyeEnum paramTyeEnum=ParamTyeEnum.paramTyeEnumValueOf(parameterType.getName());
                    if (paramTyeEnum!=null) {
                        switch (paramTyeEnum) {
                            case STRING_TYPE:
                                paramValue.add("张三");
                                break;
                            case INTEGER_TYPE:
                                paramValue.add(100);
                                break;
                            default:
                                paramValue.add(parameterType.newInstance());
                        }
                    }else {
                        paramValue.add(parameterType.newInstance());
                    }
                }
                Object obj=method.invoke(clazz.newInstance(), paramValue.toArray());
                log.info("invoke ret:"+obj);
                log.info("----------------------------");
            }

        }

    }



    public String getName(String name, String address, Integer age) {
        return name + "是一名人民教师,在" + address + "," + age + "岁";
    }

    public Student getStudentInfo(Student student) {
        return student;
    }
}
