package com.zjm.springframework.cyclicdependence;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.O;

/**
 * 使用二级缓存
 */
public class MainClassTwoCatch {
    private static Map<String, Object> singletonObiects = new HashMap<>(256);
    private static final Map<String, Object> earlySingletonObjects =
            new HashMap<>(256);

    public static void main(String[] args) throws Exception {

        Class[] classes = new Class[]{ServiceA.class, ServiceB.class};

        for (Class aClass : classes) {
            getBean(aClass);
        }

        System.out.println(getBean(ServiceA.class).getServiceB() == getBean(ServiceB.class));
        System.out.println(getBean(ServiceB.class).getServiceA() == getBean(ServiceA.class));

    }

    @SneakyThrows
    public static <T> T getBean(Class<T> beanClass) {
        String beanName = beanClass.getSimpleName();
        if (singletonObiects.containsKey(beanName)) {
            return (T) singletonObiects.get(beanName);
        }
        if (earlySingletonObjects.containsKey(beanName)) {
            return (T) earlySingletonObjects.get(beanName);
        }
        //实例化bean
        Object object = beanClass.getDeclaredConstructor().newInstance();
        //二级缓存存放实例化完成的bean
        earlySingletonObjects.put(beanName, object);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取需要注入字
            //
            // 段的class
            Class<?> fieldclass = field.getType();
            field.set(object, getBean(fieldclass));
        }
        //一级缓存存放初始化完成bean
        singletonObiects.put(beanName, object);
        earlySingletonObjects.remove(beanName);
        return (T) object;
    }

}
