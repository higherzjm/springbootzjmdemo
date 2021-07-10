package com.zjm.kindsmodels.Strategy.example2;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhujianming
 * 学生信息导入策略
 */
@Slf4j
public class StudentInfoImportStrategy extends  AbstactFileImportStrategy {
    @Override
    public boolean checkDatas(List<String> dataList) {
        log.info("学生信息导入校验");
        return true;
    }

    @Override
    public void saveDatas(List<String> dataList) {
        log.info("学生信息入库");
    }
}
