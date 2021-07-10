package com.zjm.classreflect;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;

@Slf4j
public class MainClass {
    public static void main(String[] args){
        MainClass mainClass=new MainClass();
        mainClass.test1(MyDTO.class);
    }
    public void test1(Class clazz){
        Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields){
            log.info("fieldName:"+field.getName()+":"+ Arrays.toString(field.getAnnotation(ExcelProperty.class).value()).replace("[","").replace("]","") +":"+field.getAnnotation(ColumnWidth.class).value());
        }
    }
}
