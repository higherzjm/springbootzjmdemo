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
@Api(tags = "EasyExcel�ļ�����")
public class ExcelExportControl {
 
    //����
    @PostMapping("/excelExport")
    @ApiOperation(value = "�ļ�����", notes = "�ļ�����")
    public void excelExport(HttpServletResponse response) {
        try {
            List<List<String>> headerList = new ArrayList<>();
            List<String> header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("����");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("����");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("����");
            header.add("ʡ");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("����");
            header.add("��");
            headerList.add(header);

            header = new ArrayList<>();
            header.add("����ѧ��");//���ϼ�����ͷ����
            header.add("��ҵѧУ");//�μ���ͷ����
            header.add("��ѧ");//ĩ����ͷ����
            header.add("ѧУ����");//��ĩ����ͷ����
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("��ҵѧУ");
            header.add("��ѧ");
            header.add("����");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("��ҵѧУ");
            header.add("��ѧ");
            headerList.add(header);
            header = new ArrayList<>();
            header.add("����ѧ��");
            header.add("��ҵѧУ");
            header.add("Сѧ");
            headerList.add(header);
 
            System.out.println("��ӡ����ͷ����:" + headerList.stream().map(List::size).reduce(0, Integer::max));
 
            List<List<String>> dataList = new ArrayList<>();
            List<String> data = new ArrayList<>();
            data.add("����");
            data.add("11");
            data.add("����");
            data.add("����");
            data.add("���Ŵ�ѧ");
            data.add("����");
            data.add("˫ʮ��ѧ");
            data.add("����Сѧ");
            dataList.add(data);
            data = new ArrayList<>();
            data.add("����");
            data.add("33");
            data.add("�㶫");
            data.add("����");
            data.add("������ѧ");
            data.add("ר��");
            data.add("����һ��");
            data.add("����Сѧ");
            dataList.add(data);
            //�������
            ExcelTypeEnum excelType = ExcelTypeEnum.XLSX;
            String fileName = "�ļ�����";
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + excelType.getValue(), "UTF-8"));
 
            EasyExcel.write(response.getOutputStream())
                    .head(headerList)
                    .excelType(excelType)
                    .registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 20, (short) 15))//�и߶Ȳ���:��ͷ�߶�,���ݸ߶�
                    .registerWriteHandler(new SimpleColumnWidthStyleStrategy(14))//�п�Ȳ���
                    .registerWriteHandler(this.getExcelStyle())//����Ĭ����ʽ
                    .registerWriteHandler(new MyCellStyleStrategy(dataList))//ϸ����Ԫ�����
                    .sheet("Sheet1")
                    .doWrite(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * ����excel��ʽ
     *
     * @return
     */
    public HorizontalCellStyleStrategy getExcelStyle() {
        //��������� ͷ��ͷ����ʽ ���������ݵ���ʽ �����Ĳ��Կ����Լ�ʵ��
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(buildWriteCellStyle(true), buildWriteCellStyle(false));
        return horizontalCellStyleStrategy;
    }
 
    /**
     * ���ɵ�Ԫ����ʽ
     *
     * @param isHead
     * @return
     */
    public static WriteCellStyle buildWriteCellStyle(boolean isHead) {
        //����
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
      
        writeCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());// ������ɫ
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("����");
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteFont.setBold(isHead);
        headWriteFont.setColor(IndexedColors.BLACK1.index);
        writeCellStyle.setWriteFont(headWriteFont);
        writeCellStyle.setDataFormat((short) 48);//�ı���ʽ
        writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//��ֱ����
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);//ˮƽ����
        writeCellStyle.setBorderLeft(BorderStyle.THIN);
        writeCellStyle.setBorderTop(BorderStyle.THIN);
        writeCellStyle.setBorderRight(BorderStyle.THIN);
        writeCellStyle.setBorderBottom(BorderStyle.THIN);
        writeCellStyle.setWrapped(false);
        //��Ϊ��ͷ��ʾ��ɫ
        if (!isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.RED.index);
        }
        return writeCellStyle;
    }
 
}
 
/**
 * @Description: ָ����Ԫ����ʽ����
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
 
    //ÿд��һ��Ԫ�����ݻ����һ�Σ������������ò�ͬ����ʽ
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        WriteCellStyle writeCellStyle = ExcelExportControl.buildWriteCellStyle(false);
        if (cell.getStringCellValue().contains("��ѧ") && !isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.BLUE.index);
            cell.setCellStyle(this.getWriteCellStyle(workbook, writeCellStyle));
        }
        if (cell.getStringCellValue().contains("Сѧ") && !isHead) {
            writeCellStyle.getWriteFont().setColor(IndexedColors.DARK_BLUE.index);
            cell.setCellStyle(this.getWriteCellStyle(workbook, writeCellStyle));
        }
    }
 
    //��Ĭ����ʽ�г�ȡ��Ԫ��������ʽ
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
}