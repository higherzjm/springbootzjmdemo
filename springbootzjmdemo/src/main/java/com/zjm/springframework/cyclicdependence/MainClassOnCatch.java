package com.zjm.springframework.cyclicdependence;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用一级缓存
 */
public class MainClassOnCatch {
    private static Map<String, Object> singletonObjects = new HashMap<>(256);

    public static void main(String[] args) throws Exception {

        Class[] classes = new Class[]{ServiceA.class, ServiceB.class};

        for (Class aClass : classes) {
           getBean(aClass);
        }

        System.out.println(getBean(ServiceA.class).getServiceB()==getBean(ServiceB.class));
        System.out.println(getBean(ServiceB.class).getServiceA()==getBean(ServiceA.class));

    }

    private static <T> T getBean(Class<T> beanClass) throws Exception {
        String beanName = beanClass.getSimpleName();
        if (singletonObjects.containsKey(beanName)) {
            return (T) singletonObjects.get(beanName);
        }
        Object obiect = beanClass.getDeclaredConstructor().newInstance();
        singletonObjects.put(beanName, obiect);
        Field[] fields = obiect.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取需要注入宝段的class
            Class<?> fieldclass = field.getType();
            field.set(obiect, getBean(fieldclass));
        }
        return (T) obiect;
    }

}
