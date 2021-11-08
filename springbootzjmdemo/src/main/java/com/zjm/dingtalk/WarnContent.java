package com.zjm.dingtalk;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WarnContent implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String text;


}