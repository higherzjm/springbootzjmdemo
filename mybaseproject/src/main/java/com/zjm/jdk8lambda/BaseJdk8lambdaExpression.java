package com.zjm.jdk8lambda;


import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjm.VO.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

/**
 * @author zhujianming
 */
@Slf4j
public class BaseJdk8lambdaExpression {
    public static void main(String[] args) {
        BaseJdk8lambdaExpression jdk8New = new BaseJdk8lambdaExpression();
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 20), new Student(11, "张三2", 10),
                new Student(2, "李四", 30), new Student(3, "李四2", 20), new Student(4, "王五", 30),
                new Student(5, "王五", 10));

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
        jdk8New.test16(studentList);//foreach修改
        jdk8New.test17(studentList);//allMatch、noneMatch
        jdk8New.test18(studentList);//Collectors.joining(",")字符串集合以逗号隔开合并成单字符串
        jdk8New.test19(studentList);//sort 数组排序
        jdk8New.test20(studentList);//按属性分组，并返回指定属性结果集
        jdk8New.test21(studentList);//Comparator.comparingInt排序 重新定义排序规则
        jdk8New.test22();
    }

    //转换成map,可以用在对一些集合进行分组，比如按学生年龄或姓名分组
    private void test1(List<Student> studentList) {
        log.info("----------------转换成map-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        log.info("转换成map前:" + studentMap);
        Map<Integer, String> studentIdNameMap = studentList.stream().filter(s -> s.getId() == 1 || s.getId() == 2).collect(Collectors.toMap(Student::getAge, Student::getName));
        log.info("转换成map:" + studentIdNameMap);
    }

    //获取集合中的指定信息列表，可以用在去除集合中的冗余信息，比如我就需要获取学生的姓名列表
    private void test2(List<Student> studentList) {
        log.info("----------------获取集合中的指定信息列表-------------------");
        List<String> studentNames = studentList.stream().map(Student::getName).collect(Collectors.toList());
        log.info("" + studentNames);
    }

    //过滤，按条件获取子集合
    private void test3(List<Student> studentList) {
        log.info("----------------过滤-------------------");
        List<Student> studentList1 = studentList.stream().filter(s -> s.getAge() >= 20).collect(Collectors.toList());
        log.info("" + studentList1);
    }

    //排序，数据的正序或倒序排
    private void test4(List<Student> studentList) {
        log.info("----------------排序-------------------");
        List<Student> studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        log.info("正序:" + studentList1);
        studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        log.info("倒序:" + studentList1);
    }

    //递归，跟for循环作用一样
    private void test5(List<Student> studentList) {
        log.info("----------------递归-------------------");
        log.info("list集合递归：");
        studentList.forEach(s -> {
            System.out.print(s + " ");
        });
        log.info("");
        log.info(" map递归：");
        Map<Integer, String> stringIdNameMap = studentList.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        stringIdNameMap.forEach((k, v) -> {
            System.out.print(k + ":" + v + " ");
        });
        log.info("");
    }

    //判空:orElse为空返回新的值， orElseGet为空get新值
    private void test6(List<Student> studentList) {
        log.info("----------------判空-------------------");
        List<Student> studentsNew = Optional.ofNullable(studentList).orElse(Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        log.info("" + studentsNew);
        studentsNew = Optional.ofNullable(studentList).orElseGet(() -> Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        log.info("" + studentsNew);
    }

    //去重
    private void test7(List<Student> studentList) {
        log.info("----------------去重-------------------");
        List<Integer> studentAges = studentList.stream().map(Student::getAge).distinct().collect(Collectors.toList());
        log.info("" + studentAges);
    }

    //截取
    private void test8(List<Student> studentList) {
        log.info("----------------截取-------------------");
        List<Student> studentListLimt = studentList.stream().limit(2).collect(Collectors.toList());
        log.info("" + studentListLimt);
    }

    //汇总，主要用在int，long，double类型的汇总，BigDecimal类型不支持
    private void test9(List<Student> studentList) {
        log.info("---------------- 汇总-------------------");
        IntSummaryStatistics intSummaryStatistics = studentList.stream().mapToInt(Student::getAge).summaryStatistics();
        log.info("sum:" + intSummaryStatistics.getSum() + ";max:" + intSummaryStatistics.getMax());

    }

    //条件校验,主要返回是否配某种校验条件
    private void test10(List<Student> studentList) {
        log.info("---------------- 条件校验-------------------");
        boolean checkStatus = studentList.stream().anyMatch(s -> s.getAge() > 10);
        log.info("" + checkStatus);
    }

    //集合转换，该用法很多实际情况下根据指定参数返回指定的结果值
    private void test11(List<Student> studentList) {
        log.info("----------------集合转换-------------------");
        List<Map<String, Integer>> studentNameAgeList = studentList.stream().map(s -> {
            Map<String, Integer> stringIntegerMap = new HashMap<>();
            stringIntegerMap.put(s.getName(), s.getAge());
            return stringIntegerMap;
        }).collect(Collectors.toList());
        log.info("" + studentNameAgeList);
    }

    //分组
    private void test12(List<Student> studentList) {
        log.info("----------------集合按年龄分组-------------------");
        Map<Integer, List<Student>> salaryPayrollItemGroups = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        salaryPayrollItemGroups.forEach((k, v) -> log.info("k:" + k + ";v:" + v));
    }

    //转换成map，并进行按key排序
    private void test13(List<Student> studentList) {
        log.info("----------------转换成map，并进行按key排序-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        log.info("排序前:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        log.info("正序排序后:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
        log.info("倒序排序后:" + studentMap);
    }

    //对象转换成属性map
    private void test14() {
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 10), new Student(11, "张三2", 10),
                new Student(2, "李四", 20), new Student(3, "李四2", 20), new Student(4, "王五", 30),
                new Student(5, "朱八", 30));
        Map<String, Integer> nameAgeMap = studentList.stream().collect(Collectors.toMap(
                Student::getName,
                Student::getAge
        ));
        log.info("对象转换成属性map:" + nameAgeMap);
    }

    //过滤新数据，对象拷贝，并按年龄分组
    private void test15() {
        List<Student> studentList = Arrays.asList(new Student(1, "张三", 10), new Student(2, "张三2", 10),
                new Student(3, "李四", 15), new Student(4, "李四2", 15), new Student(5, "王五", 29),
                new Student(6, "朱八", 30));
        Map<Integer, List<Student2>> nameAgeMap = studentList.stream().filter(s -> s.getAge() < 30).
                map(s -> BeanUtil.copyProperties(s, Student2.class)).collect(Collectors.groupingBy(Student2::getAge));
        log.info("过滤新数据，对象拷贝，并按年龄分组:" + nameAgeMap);
    }

    //foreach修改
    private void test16(List<Student> studentList) {
        studentList.forEach(s -> s.setName("修改"));
        log.info("修改后的集合:" + studentList);
    }

    //allMatch、noneMatch
    private void test17(List<Student> studentList) {
        log.info("studentList:" + studentList);
        boolean allMatch = studentList.stream().allMatch(s -> s.getAge() >= 10);
        log.info("allMatch:" + allMatch);
        boolean noneMatch = studentList.stream().noneMatch(s -> s.getAge() >= 30);
        log.info("noneMatch:" + noneMatch);
    }

    //Collectors.joining(",")字符串集合以逗号隔开合并成单字符串
    private void test18(List<Student> studentList) {
        log.info("----------------字符串集合以逗号隔开合并成单字符串-------------------");
        String studentNames = studentList.stream().map(Student::getName).collect(Collectors.joining(","));
        log.info("字符串集合以逗号隔开合并成单字符串:" + studentNames);
    }

    //sort 数组排序
    private void test19(List<Student> studentList) {
        log.info("----------------sort 数组排序-------------------");
        List<Student> sortRet = studentList.stream()
                .sorted((student1, student2) -> {
                    int sortBetween = student2.getAge() - student1.getAge();
                    if (sortBetween == 0) {
                        return (student1.getId() - student2.getId()) >= 0 ? -1 : 1;
                    } else {
                        return sortBetween;
                    }
                }).collect(Collectors.toList());
        log.info("sort 倒叙:" + sortRet);
        sortRet = studentList.stream()
                .sorted((student1, student2) -> {
                    int sortBetween = student1.getAge() - student2.getAge();
                    if (sortBetween == 0) {
                        return (student1.getId() - student2.getId()) >= 0 ? 1 : -1;
                    } else {
                        return sortBetween;
                    }
                }).collect(Collectors.toList());
        log.info("sort 正序" + sortRet);
    }

    //按属性分组，并返回指定属性结果集
    private void test20(List<Student> studentList) {
        log.info("----------------按属性分组，并返回指定属性结果集-------------------");
        Map<Integer, List<String>> ageMap = studentList.stream().collect(Collectors.groupingBy(Student::getAge
                , Collectors.mapping(Student::getName, Collectors.toList())));
        log.info("按属性分组，并返回指定属性结果集:" + ageMap);
    }

    //Comparator.comparingInt排序 重新定义排序规则
    private void test21(List<Student> studentList) {
        log.info("----------------Comparator.comparingInt排序 重新定义排序规则------------------");
        Map<Integer, Integer> newSortRole = Maps.newHashMap();
        newSortRole.put(20, 1);
        newSortRole.put(10, 2);
        newSortRole.put(30, 3);
        log.info("Comparator.comparingInt排序前:" + studentList);
        studentList.sort(Comparator.comparingInt(p0 -> newSortRole.get(p0.getAge())));
        log.info("Comparator.comparingInt排序后:" + studentList);
    }

    /**
     * 集合取交集【两个集合同时存在的值】
     */
    private void test22() {
        List<List<Integer>> all = Lists.newArrayList();
        List<Integer> a = Lists.newArrayList(1, 2, 3, 4, 5);
        all.add(a);
        List<Integer> b = Lists.newArrayList(1, 3, 5, 7, 9);
        all.add(b);
        List<Integer> c = Lists.newArrayList(1, 4, 5, 8, 9);
        all.add(c);

        Optional<List<Integer>> result = all.parallelStream()
                .filter(elementList -> elementList != null && elementList.size() != 0)
                .reduce((v1, v2) -> {
                    v1.retainAll(v2);
                    return v1;
                });
        log.info("ret:" + result.get());
        log.info("retainAll:" + a.retainAll(b));
        log.info("a:" + a);
        log.info("retainAll:" + c.retainAll(b));
        log.info("c:" + c);

    }

    /**
     * @description IntStream.rangeClosed 循环指定次数
     * @date 2022/3/11 15:52
     */
    @Test
    public void test23() {
    List<Student>  student2List=IntStream.rangeClosed(1, 100).mapToObj(s -> Student.builder().age(30 + s).name("张三"+s).build())
            .filter(s -> s.getAge() < 50).collect(Collectors.toList());
    log.info("student2List:{}",student2List);
    }
    /**
     * @description rangeClosed 便捷循环
     * @date 2022/3/18 10:24
     */
    @Test
    public void test24() {
        IntStream.rangeClosed(1,3).forEach(i->{
            log.info("i:{}",i);
        });
        IntStream.range(1,3).forEach(i->{
            log.info("i2:{}",i);
        });
        IntStream.rangeClosed(1,3).parallel().forEach(i->{
            log.info("i3:{}",i);
        });
        String str=IntStream.rangeClosed(1,3).mapToObj(i->"字符串").collect(Collectors.joining("-"));
        log.info("str:{}",str);
    }
    /**
     * @description IntStream.rangeClosed list转化为数值
     * @date 2022/3/11 15:52
     */
    @Test
    public void test25() {
        Student[] student2List= IntStream.rangeClosed(1, 100).mapToObj(s -> Student.builder().age(30 + s).name("张三"+s).build()).filter(s->s.getAge()<50).toArray(Student[]::new);
        log.info("student2List:{}",student2List.length);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        String[] array2 =new String[list.size()];
        String[] array = list.toArray(array2);
        log.info("array:{}",array.length);

        Integer[] integers = Stream.of(1, 2, 3, 4, 5).toArray(Integer[]::new);
        log.info("integers:{}",integers.length);

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

