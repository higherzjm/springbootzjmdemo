package com.zjm.baseapplication.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    private int age;



}
