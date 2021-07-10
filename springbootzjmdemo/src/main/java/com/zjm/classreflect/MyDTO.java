package com.zjm.classreflect;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.Serializable;

@Data
@Builder
//IndexedColors.RED.getIndex()
@ContentRowHeight(13)
@HeadRowHeight(13)
@HeadStyle(dataFormat = 49, fillForegroundColor = 1, horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.TOP, wrapped = false)
// 头字体设置成10 非粗体
@HeadFontStyle(fontHeightInPoints = 10, bold = false)
//设置文本格式 左对齐 顶端对齐 边框
@ContentStyle(dataFormat = 49, horizontalAlignment = HorizontalAlignment.LEFT, verticalAlignment = VerticalAlignment.TOP, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN)
// 内容字体设置成10
@ContentFontStyle(fontHeightInPoints = 10, fontName="宋体")
public class MyDTO implements Serializable {

    @ColumnWidth(13)
    @ExcelProperty(value = "序号（必填）")
    private Integer seq;

    @ColumnWidth(32)
    @ExcelProperty(value = "账号（必填）")
    private String bankNo;

    @ColumnWidth(13)
    @ExcelProperty(value = "户名（必填）")
    private String employeeName;

    @ColumnWidth(13)
    @ExcelProperty(value = "金额（必填）")
    private String amount;

    @ColumnWidth(34)
    @ExcelProperty(value = "跨行标识（选填 建行填0 他行填1）")
    private Integer interBankFlag;

    @ColumnWidth(40)
    @ExcelProperty(value = "行名（跨行业务与联行行号不能同时为空）")
    private String interBankName;

    @ColumnWidth(40)
    @ExcelProperty(value = "联行行号（跨行业务与行名不能同时为空）")
    private String interBankNo;

    @ColumnWidth(39)
    @ExcelProperty(value = "摘要（选填 显示在收款账户流水明细中）")
    private String description;

    @ColumnWidth(13)
    @ExcelProperty(value = "备注（选填）")
    private String remark;
}