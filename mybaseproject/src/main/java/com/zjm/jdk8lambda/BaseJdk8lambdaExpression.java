package com.zjm.jdk8lambda;


import cn.hutool.core.bean.BeanUtil;
import com.zjm.VO.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

/**
 * @author zhujianming
 */
@Slf4j
public class BaseJdk8lambdaExpression {
    public static void main(String[] args) {
        BaseJdk8lambdaExpression jdk8New = new BaseJdk8lambdaExpression();
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 10), new Student(11, "张三2", 10),
                new Student(2, "李四", 20), new Student(3, "李四2", 20), new Student(4, "王五", 30),
                new Student(5, "王五", 30));

        jdk8New.test1(studentList);//转换成map
        jdk8New.test2(studentList);//获取集合中的指定信息列表
        jdk8New.test3(studentList);//过滤
        jdk8New.test4(studentList);//排序
        jdk8New.test5(studentList);//递归
        jdk8New.test6(null);
        jdk8New.test7(studentList);//去重
        jdk8New.test8(studentList);//判空
        jdk8New.test9(studentList);//截取
        jdk8New.test10(studentList);//条件校验
        jdk8New.test11(studentList);//集合转换
        jdk8New.test12(studentList);//分组
        jdk8New.test13(studentList);//转换成map，并按key进行排序
        jdk8New.test14();//对象转换成属性map
        jdk8New.test15();//过滤新数据，对象拷贝，并按年龄分组


    }

    //转换成map,可以用在对一些集合进行分组，比如按学生年龄或姓名分组
    public void test1(List<Student> studentList) {
        System.out.println("----------------转换成map-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println(studentMap);
        Map<Integer, String> studentIdNameMap = studentList.stream().filter(s -> s.getId() == 1 || s.getId() == 2).collect(Collectors.toMap(Student::getAge, Student::getName));
        System.out.println(studentIdNameMap);
    }

    //获取集合中的指定信息列表，可以用在去除集合中的冗余信息，比如我就需要获取学生的姓名列表
    public void test2(List<Student> studentList) {
        System.out.println("----------------获取集合中的指定信息列表-------------------");
        List<String> studentNames = studentList.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(studentNames);
    }

    //过滤，按条件获取子集合
    public void test3(List<Student> studentList) {
        System.out.println("----------------过滤-------------------");
        List<Student> studentList1 = studentList.stream().filter(s -> s.getAge() >= 20).collect(Collectors.toList());
        System.out.println(studentList1);
    }

    //排序，数据的正序或倒序排
    public void test4(List<Student> studentList) {
        System.out.println("----------------排序-------------------");
        List<Student> studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        System.out.println("正序:" + studentList1);
        studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        System.out.println("倒序:" + studentList1);
    }

    //递归，跟for循环作用一样
    public void test5(List<Student> studentList) {
        System.out.println("----------------递归-------------------");
        System.out.println("list集合递归：");
        studentList.forEach(s -> {
            System.out.print(s + " ");
        });
        System.out.println("");
        System.out.println(" map递归：");
        Map<Integer, String> stringIdNameMap = studentList.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        stringIdNameMap.forEach((k, v) -> {
            System.out.print(k + ":" + v + " ");
        });
        System.out.println("");
    }

    //判空:orElse为空返回新的值， orElseGet为空get新值
    public void test6(List<Student> studentList) {
        System.out.println("----------------判空-------------------");
        List<Student> studentsNew = Optional.ofNullable(studentList).orElse(Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        System.out.println(studentsNew);
        studentsNew = Optional.ofNullable(studentList).orElseGet(() -> Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        System.out.println(studentsNew);
    }

    //去重
    public void test7(List<Student> studentList) {
        System.out.println("----------------去重-------------------");
        List<Integer> studentAges = studentList.stream().map(Student::getAge).distinct().collect(Collectors.toList());
        System.out.println(studentAges);
    }

    //截取
    public void test8(List<Student> studentList) {
        System.out.println("----------------截取-------------------");
        List<Student> studentListLimt = studentList.stream().limit(2).collect(Collectors.toList());
        System.out.println(studentListLimt);
    }

    //汇总，主要用在int，long，double类型的汇总，BigDecimal类型不支持
    public void test9(List<Student> studentList) {
        System.out.println("---------------- 汇总-------------------");
        IntSummaryStatistics intSummaryStatistics = studentList.stream().mapToInt(Student::getAge).summaryStatistics();
        System.out.println("sum:" + intSummaryStatistics.getSum() + ";max:" + intSummaryStatistics.getMax());

    }

    //条件校验,主要返回是否配某种校验条件
    public void test10(List<Student> studentList) {
        System.out.println("---------------- 条件校验-------------------");
        boolean checkStatus = studentList.stream().anyMatch(s -> s.getAge() > 10);
        System.out.println(checkStatus);
    }

    //集合转换，该用法很多实际情况下根据指定参数返回指定的结果值
    public void test11(List<Student> studentList) {
        System.out.println("----------------集合转换-------------------");
        List<Map<String, Integer>> studentNameAgeList = studentList.stream().map(s -> {
            Map<String, Integer> stringIntegerMap = new HashMap<>();
            stringIntegerMap.put(s.getName(), s.getAge());
            return stringIntegerMap;
        }).collect(Collectors.toList());
        System.out.println(studentNameAgeList);
    }

    //分组
    public void test12(List<Student> studentList) {
        System.out.println("----------------集合按年龄分组-------------------");
        Map<Integer, List<Student>> salaryPayrollItemGroups = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        salaryPayrollItemGroups.forEach((k, v) -> System.out.println("k:" + k + ";v:" + v));
    }

    //转换成map，并进行按key排序
    public void test13(List<Student> studentList) {
        System.out.println("----------------转换成map，并进行按key排序-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println("排序前:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("正序排序后:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
        System.out.println("倒序排序后:" + studentMap);
    }

    //对象转换成属性map
    public void test14() {
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 10), new Student(11, "张三2", 10),
                new Student(2, "李四", 20), new Student(3, "李四2", 20), new Student(4, "王五", 30),
                new Student(5, "朱八", 30));
        Map<String, Integer> nameAgeMap = studentList.stream().collect(Collectors.toMap(
                Student::getName,
                Student::getAge
        ));
        System.out.println("对象转换成属性map:" + nameAgeMap);
    }

    //过滤新数据，对象拷贝，并按年龄分组
    public void test15() {
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 10), new Student(2, "张三2", 10),
                new Student(3, "李四", 15), new Student(4, "李四2", 15), new Student(5, "王五", 29),
                new Student(6, "朱八", 30));
        Map<Integer, List<Student2>> nameAgeMap = studentList.stream().filter(s -> s.getAge() < 30).
                map(s -> BeanUtil.copyProperties(s, Student2.class)).collect(Collectors.groupingBy(Student2::getAge));
        System.out.println("过滤新数据，对象拷贝，并按年龄分组:" + nameAgeMap);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Student2 {
        private String name;
        private int age;

        @Override
        public String toString() {
            return "Student2{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}

