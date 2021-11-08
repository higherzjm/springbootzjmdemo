package com.zjm.dingtalk;

import java.io.Serializable;

/**
 * @Author: linxiongwei
 * @Date: 2021-03-29
 * @Description:
 */
public class WarnResult implements Serializable {

    /**
     * 异常代码, 0 正常 其他正常
     */
    private Integer errcode;

    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
