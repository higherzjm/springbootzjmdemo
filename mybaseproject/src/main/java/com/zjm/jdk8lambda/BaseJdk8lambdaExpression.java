package com.zjm.jdk8lambda;


import cn.hutool.core.bean.BeanUtil;
import com.zjm.VO.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        List<Student> studentList = Arrays.asList(new Student(1, "����", 10), new Student(11, "����2", 10),
                new Student(2, "����", 20), new Student(3, "����2", 20), new Student(4, "����", 30),
                new Student(5, "����", 30));

        jdk8New.test1(studentList);//ת����map
        jdk8New.test2(studentList);//��ȡ�����е�ָ����Ϣ�б�
        jdk8New.test3(studentList);//����
        jdk8New.test4(studentList);//����
        jdk8New.test5(studentList);//�ݹ�
        jdk8New.test6(null);
        jdk8New.test7(studentList);//ȥ��
        jdk8New.test8(studentList);//�п�
        jdk8New.test9(studentList);//��ȡ
        jdk8New.test10(studentList);//����У��
        jdk8New.test11(studentList);//����ת��
        jdk8New.test12(studentList);//����
        jdk8New.test13(studentList);//ת����map������key��������
        jdk8New.test14();//����ת��������map
        jdk8New.test15();//���������ݣ����󿽱��������������
        jdk8New.test16(studentList);//foreach�޸�
        jdk8New.test17(studentList);//allMatch��noneMatch
        jdk8New.test18(studentList);//Collectors.joining(",")�ַ��������Զ��Ÿ����ϲ��ɵ��ַ���


    }

    //ת����map,�������ڶ�һЩ���Ͻ��з��飬���簴ѧ���������������
    public void test1(List<Student> studentList) {
        System.out.println("----------------ת����map-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println(studentMap);
        Map<Integer, String> studentIdNameMap = studentList.stream().filter(s -> s.getId() == 1 || s.getId() == 2).collect(Collectors.toMap(Student::getAge, Student::getName));
        System.out.println(studentIdNameMap);
    }

    //��ȡ�����е�ָ����Ϣ�б���������ȥ�������е�������Ϣ�������Ҿ���Ҫ��ȡѧ���������б�
    public void test2(List<Student> studentList) {
        System.out.println("----------------��ȡ�����е�ָ����Ϣ�б�-------------------");
        List<String> studentNames = studentList.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(studentNames);
    }

    //���ˣ���������ȡ�Ӽ���
    private void test3(List<Student> studentList) {
        System.out.println("----------------����-------------------");
        List<Student> studentList1 = studentList.stream().filter(s -> s.getAge() >= 20).collect(Collectors.toList());
        System.out.println(studentList1);
    }

    //�������ݵ����������
    private void test4(List<Student> studentList) {
        System.out.println("----------------����-------------------");
        List<Student> studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        System.out.println("����:" + studentList1);
        studentList1 = studentList.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        System.out.println("����:" + studentList1);
    }

    //�ݹ飬��forѭ������һ��
    private void test5(List<Student> studentList) {
        System.out.println("----------------�ݹ�-------------------");
        System.out.println("list���ϵݹ飺");
        studentList.forEach(s -> System.out.print(s + " "));
        System.out.println();
        System.out.println(" map�ݹ飺");
        Map<Integer, String> stringIdNameMap = studentList.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        stringIdNameMap.forEach((k, v) -> {
            System.out.print(k + ":" + v + " ");
        });
        System.out.println();
    }

    //�п�:orElseΪ�շ����µ�ֵ�� orElseGetΪ��get��ֵ
    private void test6(List<Student> studentList) {
        System.out.println("----------------�п�-------------------");
        List<Student> studentsNew = Optional.ofNullable(studentList).orElse(Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        System.out.println(studentsNew);
        studentsNew = Optional.ofNullable(studentList).orElseGet(() -> Arrays.asList(new Student(22, "aa", 101), new Student(22, "bb", 102)));
        System.out.println(studentsNew);
    }

    //ȥ��
    private void test7(List<Student> studentList) {
        System.out.println("----------------ȥ��-------------------");
        List<Integer> studentAges = studentList.stream().map(Student::getAge).distinct().collect(Collectors.toList());
        System.out.println(studentAges);
    }

    //��ȡ
    private void test8(List<Student> studentList) {
        System.out.println("----------------��ȡ-------------------");
        List<Student> studentListLimt = studentList.stream().limit(2).collect(Collectors.toList());
        System.out.println(studentListLimt);
    }

    //���ܣ���Ҫ����int��long��double���͵Ļ��ܣ�BigDecimal���Ͳ�֧��
    private void test9(List<Student> studentList) {
        System.out.println("---------------- ����-------------------");
        IntSummaryStatistics intSummaryStatistics = studentList.stream().mapToInt(Student::getAge).summaryStatistics();
        System.out.println("sum:" + intSummaryStatistics.getSum() + ";max:" + intSummaryStatistics.getMax());

    }

    //����У��,��Ҫ�����Ƿ���ĳ��У������
    private void test10(List<Student> studentList) {
        System.out.println("---------------- ����У��-------------------");
        boolean checkStatus = studentList.stream().anyMatch(s -> s.getAge() > 10);
        System.out.println(checkStatus);
    }

    //����ת�������÷��ܶ�ʵ������¸���ָ����������ָ���Ľ��ֵ
    private void test11(List<Student> studentList) {
        System.out.println("----------------����ת��-------------------");
        List<Map<String, Integer>> studentNameAgeList = studentList.stream().map(s -> {
            Map<String, Integer> stringIntegerMap = new HashMap<>();
            stringIntegerMap.put(s.getName(), s.getAge());
            return stringIntegerMap;
        }).collect(Collectors.toList());
        System.out.println(studentNameAgeList);
    }

    //����
    private void test12(List<Student> studentList) {
        System.out.println("----------------���ϰ��������-------------------");
        Map<Integer, List<Student>> salaryPayrollItemGroups = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        salaryPayrollItemGroups.forEach((k, v) -> System.out.println("k:" + k + ";v:" + v));
    }

    //ת����map�������а�key����
    private void test13(List<Student> studentList) {
        System.out.println("----------------ת����map�������а�key����-------------------");
        Map<Integer, Student> studentMap = studentList.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println("����ǰ:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("���������:" + studentMap);
        studentMap = studentMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
        System.out.println("���������:" + studentMap);
    }

    //����ת��������map
    private void test14() {
        System.out.println("----------------ת����map�������а�key����-------------------");
        List<Student> studentList = Arrays.asList(new Student(1, "����", 10), new Student(11, "����2", 10),
                new Student(2, "����", 20), new Student(3, "����2", 20), new Student(4, "����", 30),
                new Student(5, "���", 30));
        Map<String, Integer> nameAgeMap = studentList.stream().collect(Collectors.toMap(
                Student::getName,
                Student::getAge
        ));
        System.out.println("����ת��������map:" + nameAgeMap);
    }

    //���������ݣ����󿽱��������������
    private void test15() {
        System.out.println("----------------���������ݣ����󿽱��������������-------------------");
        List<Student> studentList = Arrays.asList(new Student(1, "����", 10), new Student(2, "����2", 10),
                new Student(3, "����", 15), new Student(4, "����2", 15), new Student(5, "����", 29),
                new Student(6, "���", 30));
        Map<Integer, List<Student2>> nameAgeMap = studentList.stream().filter(s -> s.getAge() < 30).
                map(s -> BeanUtil.copyProperties(s, Student2.class)).collect(Collectors.groupingBy(Student2::getAge));
        System.out.println("���������ݣ����󿽱��������������:" + nameAgeMap);
    }

    //foreach�޸�
    private void test16(List<Student> studentList) {
        System.out.println("----------------foreach�޸�-------------------");
        studentList.forEach(s -> s.setName("�޸�->"+s.getName()));
        System.out.println("�޸ĺ�ļ���:" + studentList);
    }

    //allMatch��noneMatch
    private void test17(List<Student> studentList) {
        System.out.println("----------------allMatch��noneMatch-------------------");
        System.out.println("studentList:"+studentList);
        boolean allMatch = studentList.stream().allMatch(s -> s.getAge() >= 10);
        System.out.println("allMatch:" + allMatch);
        boolean noneMatch = studentList.stream().noneMatch(s -> s.getAge() >=   30);
        System.out.println("noneMatch:" + noneMatch);
    }
    //Collectors.joining(",")�ַ��������Զ��Ÿ����ϲ��ɵ��ַ���
    private void test18(List<Student> studentList) {
        System.out.println("----------------�ַ��������Զ��Ÿ����ϲ��ɵ��ַ���-------------------");
        String studentNames = studentList.stream().map(Student::getName).collect(Collectors.joining(","));
        System.out.println("�ַ��������Զ��Ÿ����ϲ��ɵ��ַ���:"+studentNames);
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

