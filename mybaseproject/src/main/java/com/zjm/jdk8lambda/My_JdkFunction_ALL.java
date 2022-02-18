package com.zjm.jdk8lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.*;

/**
 * jdk函数使用汇总
 */
@Slf4j
public class My_JdkFunction_ALL {
    //根据指定参数返回新的结果
    @Test
    public void biFunction() {
        Function<String, String> function = (name) -> {
            log.info("BiFunction 函数调用,一个参数，一个返回值");
            return "我是" + name;
        };
        String ret = function.apply("张三");
        log.info("ret：{}", ret);
        BiFunction<String, Integer, String> biFunction = (name, age) -> {
            log.info("BiFunction 函数调用,两个参数，一个返回值");
            return "我是" + name + "年龄:" + age;
        };
        ret = biFunction.apply("张三", 100);
        log.info("ret：{}", ret);
    }

    //条件判断
    @Test
    public void predicate() {
        Predicate<String> filter = (name) -> {
            if (name.equals("张三")) {
                return true;
            } else {
                return false;
            }
        };
        if (filter.test("张三")) {
            log.info("单参数，校验通过");
        } else {
            log.info("单参数，校验不通过");
        }

        BiPredicate<String, Integer> filter2 = (name, age) -> {
            if (name.equals("张三") && age == 30) {
                return true;
            } else {
                return false;
            }
        };

        if (filter2.test("张三", 30)) {
            log.info("双参数，校验通过");
        } else {
            log.info("双参数，校验不通过");
        }
    }

    //先定义函数内容，后进行调用
    @Test
    public void consumer() {
        log.info("一个参数");
        Consumer<Student> consumer = (student) -> {
            student.setAge(30);
            student.setName("张三");
        };
        Student student = Student.builder().build();
        consumer.accept(student);
        log.info("student:{}", student);

        log.info("两个参数");
        BiConsumer<Student, String> consumer2 = (student2, s) -> {
            student2.setAge(30);
            student2.setName(s + "张三");
        };
        Student student2 = Student.builder().build();
        consumer2.accept(student2, "福建");
        log.info("student2:{}", student2);
    }

    //读取定义对象
    @Test
    public void supplier() {
        Supplier<Student> supplier = () -> {
            log.info("定义对象");
            return Student.builder().age(30).name("张三").build();
        };

        Student student = supplier.get();
        log.info("student:{}", student);
    }

    //比较器
    @Test
    public void comparator() {
        Comparator<Integer> comparator = (num1, num2) -> {
            if (num1 == num2) {
                return 0;
            } else if (num1 > num2) {
                return 1;
            } else {
                return -1;
            }
        };

        log.info("ret:" + comparator.compare(100, 90));
        log.info("ret:" + comparator.reversed().compare(100, 90));
        log.info("ret:" + comparator.compare(100, 100));
    }
    //比较器
    @Test
    public void binaryOperator() {
        Comparator<Integer> comparator = (num1, num2) -> {
            if (num1.equals(num2)) {
                return 0;
            } else if (num1 > num2) {
                return 1;
            } else {
                return -1;
            }
        };

        int apply = BinaryOperator.maxBy(comparator).apply(100, 90);
        log.info("获取更大的:" + apply);
        apply = BinaryOperator.minBy(comparator).apply(100, 90);
        log.info("获取更小的:" + apply);
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student {
        private int id;
        private String name;
        private int age;


    }
}
