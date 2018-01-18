package com.myapp.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月17日 
 * @system:
 * 基于easypoi整理的一套工具类
 *-----------MySong---------------
 */
public class MyExcelExportUtil {
	
	private static Workbook getWorkbook(ExcelType type, int size) {
        if (ExcelType.HSSF.equals(type)) {
            return new HSSFWorkbook();
        } else if (size < 100000) {
            return new XSSFWorkbook();
        } else {
            return new SXSSFWorkbook();
        }
    }
	public static Workbook exportExcel(ExportParams entity,
			List<ExcelExportEntity> entityList,
			Collection<? extends Map<?, ?>> dataSet) {
		Workbook workbook = getWorkbook(entity.getType(), dataSet.size());
		new MyExcelExportServer().createSheetForMap(workbook, entity, entityList,dataSet);
		return workbook;
	}
}
