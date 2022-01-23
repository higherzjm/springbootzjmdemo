package com.zjm.springframework.cyclicdependence;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用三级缓存
 */
public class MainClassThreeCatch {
    private static Map<String, Object> singletonObiects = new HashMap<>(256);
    private static final Map<String, Object> earlySingletonObjects =
            new HashMap<>(256);
    private static final Map<String, ObjectFactory> singletonFactories =
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
        ObjectFactory<?> singletonFactory = (ObjectFactory<?>) singletonFactories.get(beanName);
        if (singletonFactory != null) {
            return (T) singletonFactory.getObject();
        }
        //实例化bean
        Object object = beanClass.getDeclaredConstructor().newInstance();
        singletonFactories.put(beanName,()->{
            Object proxy = createProxy(object);
            singletonFactories.remove(beanName);
            earlySingletonObjects.put(beanName, proxy);
            return proxy;
        });
        //开始初始化bean，即填充属性
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取需要注入宝段的class
            Class<?> fieldclass = field.getType();
            field.set(object, getBean(fieldclass));
        }
        createProxy(object);
        singletonObiects.put(beanName, object);
        earlySingletonObjects.remove(beanName);
        return (T) object;
    }

    private static Object createProxy(Object object) {
        return object;
    }

}
