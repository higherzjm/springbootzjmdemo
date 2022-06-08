package com.zjm.my_httpretry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhujianming
 * @description
 * @date 2022/6/8 13:57
 */
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseResult {
    private String code;
    private String msg;
}
