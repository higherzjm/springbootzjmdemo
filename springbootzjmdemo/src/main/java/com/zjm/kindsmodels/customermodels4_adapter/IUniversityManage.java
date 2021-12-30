package com.zjm.kindsmodels.customermodels4_adapter;

/**
 * 高校管理
 */
public interface IUniversityManage {
    /**
     * 获取地址
     * @param name 学校名称
     * @return
     */
    String getAddress(String name);

    boolean supports(IUniversityManage manage);
}
