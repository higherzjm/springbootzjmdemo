package com.zjm.alibabaEasyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Font;
 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/aliBaBaEasyExcel")
@RestController
@Slf4j
@Api(tags = "EasyExcel文件导出")
public class ExcelExportControl {
 
    //导出
    @PostMapping("/excelExport")
    @ApiOperation(value = "文件导出", notes = "文件导出")
    public void excelExport(HttpServletResponse response) {
        try {
            List<List<String>> headerList = new ArrayList<>();
            List<String> header = new ArrayList<>();
            header.add("厦门学区");
            header.add("姓名");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("年龄");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("户籍");
            header.add("省");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("户籍");
            header.add("市");
            headerList.add(header);

            header = new ArrayList<>();
            header.add("厦门学区");//最上级单表头名称
            header.add("毕业学校");//次级表头名称
            header.add("大学");//末级表头名称
            header.add("学校名称");//最末级表头名称
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("毕业学校");
            header.add("大学");
            header.add("级别");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("毕业学校");
            header.add("中学");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("厦门学区");
            header.add("毕业学校");
            header.add("小学");
            headerList.add(header);
 
            System.out.println("打印出表头行数:" + headerList.stream().map(List::size).reduce(0, Integer::max));
 
            List<List<String>> dataList = new ArrayList<>();
            List<String> data = new ArrayList<>();
            data.add("张三");
            data.add("11");
            data.add("福建");
            data.add("厦门");
            data.add("厦门大学");
            data.add("本科");
            data.add("双十中学");
            data.add("蔡塘小学");
            dataList.add(data);
            data = new ArrayList<>();
            data.add("李四");
            data.add("33");
            data.add("广东");
            data.add("广州");
            data.add("集美大学");
            data.add("专科");
            data.add("厦门一中");
            data.add("何厝小学");
            dataList.add(data);
            //表格类型
            ExcelTypeEnum excelType = ExcelTypeEnum.XLSX;
            String fileName = "文件名称";
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + excelType.getValue(), "UTF-8"));
 
            EasyExcel.write(response.getOutputStream())
                    .head(headerList)
                    .excelType(excelType)
                    .registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 20, (short) 15))//行高度策略:表头高度,内容高度
                    .registerWriteHandler(new SimpleColumnWidthStyleStrategy(14))//列宽度策略
                    .registerWriteHandler(this.getExcelStyle())//设置默认样式
                    .registerWriteHandler(new MyCellStyleStrategy(dataList))//细化单元格策略
                    .sheet("Sheet1")
                    .doWrite(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 设置excel样式
     *
     * @return
     */
    public HorizontalCellStyleStrategy getExcelStyle() {
        //这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(buildWriteCellStyle(true), buildWriteCellStyle(false));
        return horizontalCellStyleStrategy;
    }
 
    /**
     * 生成单元格样式
     *
     * @param isHead
     * @return
     */
    public static WriteCellStyle buildWriteCellStyle(boolean isHead) {
        //策略
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
      
        writeCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());// 背景白色
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteFont.setBold(isHead);
        headWriteFont.setColor(IndexedColors.BLACK1.index);
        writeCellStyle.setWriteFont(headWriteFont);
        writeCellStyle.setDataFormat((short) 48);//文本格式
        writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);//水平居中
        writeCellStyle.setBorderLeft(BorderStyle.THIN);
        writeCellStyle.setBorderTop(BorderStyle.THIN);
        writeCellStyle.setBorderRight(BorderStyle.THIN);
        writeCellStyle.setBorderBottom(BorderStyle.THIN);
        writeCellStyle.setWrapped(false);
        //不为表头显示红色
        if (!isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.RED.index);
        }
        return writeCellStyle;
    }
 
}
 
/**
 * @Description: 指定单元格样式处理
 * @date 2021/1/17 13:07
 * @return
 */
class MyCellStyleStrategy extends AbstractCellWriteHandler {
    private List<List<String>> dataList;
 
    public List<List<String>> getDataList() {
        return dataList;
    }
 
    public MyCellStyleStrategy(List<List<String>> dataList) {
        this.dataList = dataList;
    }
 
    public void setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
    }
 
    //每写入一单元格数据会调用一次，可以依次设置不同的样式
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        WriteCellStyle writeCellStyle = ExcelExportControl.buildWriteCellStyle(false);
        if (cell.getStringCellValue().contains("大学") && !isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.BLUE.index);
            cell.setCellStyle(this.getWriteCellStyle(workbook, writeCellStyle));
        }
        if (cell.getStringCellValue().contains("小学") && !isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.DARK_BLUE.index);
            cell.setCellStyle(this.getWriteCellStyle(workbook, writeCellStyle));
        }
    }
 
    //从默认样式中抽取单元格字体样式
    public CellStyle getWriteCellStyle(Workbook workbook, WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        cellStyle.setFont(font);
        if (writeCellStyle.getWriteFont().getColor() != null) {
            font.setColor(writeCellStyle.getWriteFont().getColor());
        }
        cellStyle.setBorderBottom(writeCellStyle.getBorderBottom());
        cellStyle.setBorderTop(writeCellStyle.getBorderTop());
        cellStyle.setBorderLeft(writeCellStyle.getBorderLeft());
        cellStyle.setBorderRight(writeCellStyle.getBorderRight());
        if (writeCellStyle.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(writeCellStyle.getHorizontalAlignment());
        }
        if (writeCellStyle.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(writeCellStyle.getVerticalAlignment());
        }
        return cellStyle;
    }

    public MyCellStyleStrategy() {
        System.out.println("初始化ExcelExportControl");
    }
}