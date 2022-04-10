package com.zjm.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 * @description
 * @date 2022/4/10 14:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class Students {
    private String id;
    private String name;
    private Integer age;
}
