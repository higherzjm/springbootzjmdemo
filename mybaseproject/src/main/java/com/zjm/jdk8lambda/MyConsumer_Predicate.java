package com.zjm.jdk8lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Consumer  函数+校验
 */
@Slf4j
public class MyConsumer_Predicate {
    List<String> manualSingletonNames = new ArrayList<>();
    private final Map<String, String> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * Consumer,Predicate:公共类库回调
     */
    @Test
    public void test1() {
        manualSingletonNames.add("beanName1");
        manualSingletonNames.add("beanName2");
        manualSingletonNames.add("beanName3");
        log.info("before:"+manualSingletonNames);
        removeManualSingletonName("beanName2");
        log.info("after:"+manualSingletonNames);
    }

    private void removeManualSingletonName(String beanName) {
        updateManualSingletonNames(set -> set.remove(beanName), set -> set.contains(beanName));
    }

    /**
     * Update the factory's internal set of manual singleton names.
     *
     * @param action    函数处理
     * @param condition 条件校验
     */
    private void updateManualSingletonNames(Consumer<List<String>> action, Predicate<List<String>> condition) {
        synchronized (this.beanDefinitionMap) {
            if (condition.test(this.manualSingletonNames)) {
                List<String> updatedSingletons = new ArrayList<>(this.manualSingletonNames);
                action.accept(updatedSingletons);
                this.manualSingletonNames = updatedSingletons;
            }
        }

        synchronized (this.beanDefinitionMap) {
            if (condition.test(this.manualSingletonNames)) {
                action.accept(manualSingletonNames);
            }
        }
    }


    /**
     *参数值回调
     */
    @Test
    public void test2() {
        String ret=start();
        log.info("ret:"+ret);
    }


    public String start(){
        return  paramFunc(students->{
            return students.getName("张三",30);
        });
    }

    public String  paramFunc(IParamInt paramInt){
        Students students=new Students();
         String ret=paramInt.getName(students);
         ret="我的身份信息,"+ret;
         return ret;
    }



    public interface IParamInt {
        String getName(Students students);
        /**
         * 只能有一个方法，不然调用方无法区分是那种参数类型，属于唯一匹配
         */
         //Integer getAge(Integer age);
    }

    class Students {
        public String getName(String name, Integer age) {
            return "姓名:" + name + ",年龄:" + age ;
        }
    }
}
