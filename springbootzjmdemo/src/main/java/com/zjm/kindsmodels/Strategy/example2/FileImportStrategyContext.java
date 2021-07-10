package com.zjm.kindsmodels.Strategy.example2;

import org.junit.Test;

/**
 * @author zhujianming
 * �ļ�������Ի���(ѡ����һ�ֻ�������)
 */
public class FileImportStrategyContext {
    /**
     * @Description: ѧ����Ϣ����
     **/
    @Test
    public void test1(){
        AbstactFileImportStrategy abstactFileImportStrategy=new StudentInfoImportStrategy();
        abstactFileImportStrategy.importFIle("D:/learn/����/ѧ����Ϣ.xlsx");
    }
    /**
     * @Description: ��ʦ��Ϣ����
     **/
    @Test
    public void test2(){
        AbstactFileImportStrategy abstactFileImportStrategy=new TeachertInfoImportStrategy();
        abstactFileImportStrategy.importFIle("D:/learn/����/��ʦ��Ϣ.xlsx");
    }
}
