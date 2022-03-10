package com.zjm.my_clone;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhujianming
 * @description
 *  浅拷贝：只复制内容，内存地址还是指向了原来的地址，如果原来对象的值修改了，复制后的那个对象也会修改
 *  深拷贝：复制后的对象会开辟新的地址，原来对象值修改后不会影响复制后对象的值
 * @date 2022/3/10 14:41
 */
@Slf4j
public class MainClass {
    public static void main(String[] args) throws CloneNotSupportedException {
        Teacher teacher = new Teacher();
        teacher.name = "原老师";
        teacher.age = 28;

        Student student = new Student();
        student.name = "原学生";
        student.age = 18;
        student.teacher = teacher;

        MainClass mainClass = new MainClass();
        //mainClass.test1(teacher);
        mainClass.test2(teacher);
        //mainClass.test3(teacher,student);
    }
    /**
     * @description clone简单对象可以进行深拷贝
     * @date 2022/3/10 16:00
     */
    private void test1(Teacher teacher){
        Teacher teacher2= teacher;
        teacher.name="原老师2";
        log.info("teacher1:{}",teacher.toString());
        log.info("teacher2:{}",teacher2.toString());
    }
    /**
     * @description clone简单对象可以进行深拷贝
     * @date 2022/3/10 16:00
     */
    private void test2(Teacher teacher) throws CloneNotSupportedException {
        Teacher teacher2= (Teacher) teacher.clone();
        teacher.name="原老师2";
        log.info("teacher1:{}",teacher.toString());
        log.info("teacher2:{}",teacher2.toString());
    }
    /**
     * @description  对象中存在对象属性，直接拷贝会是浅拷贝，深拷贝需要单独对对象的对象属性进行拷贝
     * @date 2022/3/10 16:00
     */
    private void test3(Teacher teacher, Student student1) throws CloneNotSupportedException {

        Student student2 = (Student) student1.clone();
        System.out.println("-------------拷贝后-------------");
        System.out.println(student2.name);
        System.out.println(student2.age);
        System.out.println(student2.teacher.name);
        System.out.println(student2.teacher.age);

        System.out.println("-------------修改老师的信息后-------------");
// 修改老师的信息
        teacher.name = "新老师";


        System.out.println("student1的teacher为： " + student1.teacher.name);
        System.out.println("student2的teacher为： " + student2.teacher.name);
    }
}
