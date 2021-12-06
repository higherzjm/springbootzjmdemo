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
     * @param action    the modification action
     * @param condition a precondition for the modification action
     *                  (if this condition does not apply, the action can be skipped)
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

    @Test
    public void test2() {
        log.info(bb());
    }

    public String bb(){
        return  aa(a->{
            return a.toUpperCase();
        });
    }

    public String  aa(Test1 test){
        return  test.getName("aa");
    }



    public interface Test1 {
        @Nullable
        String getName(String a);
    }
    class StudentInfImpl implements Test1{

        @Override
        public String getName(String a) {
            return a;
        }
    }

}
