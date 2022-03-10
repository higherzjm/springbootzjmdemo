package com.zjm.my_clone;

import lombok.Data;

@Data
class Teacher implements Cloneable {
    public String name;
    public int age;
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}