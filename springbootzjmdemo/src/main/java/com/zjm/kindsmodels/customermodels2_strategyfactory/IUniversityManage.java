package com.zjm.kindsmodels.customermodels2_strategyfactory;

/**
 * 高校管理
 */
public interface IUniversityManage {
    /**
     * 通过高校名称获取地址
     * @param name 学校名称
     * @return
     */
    String getAddress(String name);
}
