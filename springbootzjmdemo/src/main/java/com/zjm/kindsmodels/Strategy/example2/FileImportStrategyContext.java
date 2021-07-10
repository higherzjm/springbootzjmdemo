package com.zjm.kindsmodels.Strategy.example2;

import org.junit.Test;

/**
 * @author zhujianming
 * 文件导入策略环境(选择哪一种环境策略)
 */
public class FileImportStrategyContext {
    /**
     * @Description: 学生信息导入
     **/
    @Test
    public void test1(){
        AbstactFileImportStrategy abstactFileImportStrategy=new StudentInfoImportStrategy();
        abstactFileImportStrategy.importFIle("D:/learn/资料/学生信息.xlsx");
    }
    /**
     * @Description: 教师信息导入
     **/
    @Test
    public void test2(){
        AbstactFileImportStrategy abstactFileImportStrategy=new TeachertInfoImportStrategy();
        abstactFileImportStrategy.importFIle("D:/learn/资料/教师信息.xlsx");
    }
}
