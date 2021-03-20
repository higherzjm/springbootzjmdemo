package com.springboot.redis;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhujianming
 */
@Builder
@Data
public class Students {
    private String name;
    private Integer age;
}
