package com.zjm.alibabaEasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelImportDTO implements Serializable {
    /**
     * 员工编码
     */
    @ExcelProperty(index = 0)
    private String personalNo;
    /**
     * 员工姓名
     */
    @ExcelProperty(index = 1)
    private String name;
    /**
     * 年
     */
    @ExcelProperty(index = 2)
    private String year;
    /**
     * 月
     */
    @ExcelProperty(index = 3)
    private String month;
}
