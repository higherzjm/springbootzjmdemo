package com.zjm.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo{
    private String name;
    private Integer age;
}