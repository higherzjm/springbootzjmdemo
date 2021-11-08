package com.zjm.alibabaEasyExcel;

import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/aliBaBaEasyExcel")
@RestController
@Slf4j
@Api(tags = "EasyExcel�ļ��ϴ�")
public class ExcelImportControl {

    //�ļ��ϴ�
    @PostMapping("/excelImport/{actionNum}")
    @ApiOperation(value = "�ļ��ϴ�", notes = "�ļ��ϴ�")
    public String excelExport(@ApiParam(name = "actionNum", value = "�������") @PathVariable("actionNum") String actionNum,
                            @ApiParam(name = "file", value = "����excel�ļ�", required = true) @RequestParam(value = "file") MultipartFile file) {
        List<ExcelImportDTO> excelImportDTOList=getExcelData(file);
        log.info("actionNum:"+actionNum+";excelImportDTOList:"+ excelImportDTOList);
        return actionNum+":"+excelImportDTOList.toString();

    }

    List<ExcelImportDTO> getExcelData(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            List<ExcelImportDTO> importDTOList = EasyExcel.read(is).head(ExcelImportDTO.class).sheet().headRowNumber(2).doReadSync();
            return importDTOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}