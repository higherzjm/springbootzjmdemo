package com.zjm.my_clone;

class Student implements Cloneable {
    public String name;
    public int age;
    public Teacher teacher;

    public Object clone() throws CloneNotSupportedException {
        // 浅复制时：只复制内容，内存地址还是指向了原来的地址，如果原来对象的值修改了，复制后的那个对象也会修改
        // return super.clone();
        // 深复制： 复制内容，复制后的对象会开辟新的地址，原来对象值修改后不会影响复制后对象的值
        return highClone();


    }

    // 深复制
    private Object highClone() throws CloneNotSupportedException {
        // 深复制时：
        Student student = (Student) super.clone();
        // 本来是浅复制，现在将Teacher对象复制一份并重新赋值进来
        student.teacher = (Teacher) this.teacher.clone();
        return student;
    }
}