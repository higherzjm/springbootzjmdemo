package com.zjm.redis.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhujianming
 */
@Builder
@Data
@AllArgsConstructor
public class Students {
    private String name;
    private Integer age;
}
