package com.myapp.test.easypoidemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.imports.ExcelImportServer;

/**
 * -----------MySong--------------- 
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月7日
 * @system:
 * -----------MySong---------------
 */
public class ExcelImportTest {

	public static void exportTemp(){
		try{
		ExportParams params =  new ExportParams("用户信息表","用户信息表导入模板  V1", "用户信息表");
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(new ExcelExportEntity("姓名", "name",20));
		entitys.add(new ExcelExportEntity("编码", "number",20));
		Workbook workbook = ExcelExportUtil.exportExcel(params, entitys, new ArrayList()); 
		Sheet sheet = workbook.getSheetAt(0);
		
		CellStyle cellStyle= workbook.createCellStyle(); 
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.DOTTED); //下边框    
		cellStyle.setBorderLeft(BorderStyle.DOTTED);//左边框    
		cellStyle.setBorderTop(BorderStyle.DOTTED);//上边框    
		cellStyle.setBorderRight(BorderStyle.DOTTED);//右边框   
		cellStyle.setLocked(true);
		Row titleRow = sheet.getRow(2);
		
		Row headRow = sheet.createRow(3);
		for(int i=0;i<entitys.size();i++){
			Cell cell = headRow.createCell(i);
			cell.setCellValue(entitys.get(i).getKey().toString());
			Cell tCell = titleRow.getCell(i);
			tCell.setCellStyle(cellStyle);
		}
		headRow.setZeroHeight(true);
		Row row = sheet.createRow(10);
		row.setHeight((short)800);
		Cell cell = row.createCell(0);
		
		cell.setCellStyle(cellStyle);
		row.createCell(1).setCellStyle(cellStyle);
		StringBuffer rtx = new StringBuffer();
		rtx.append("填写备注:");
		rtx.append("\r\n\t 1:姓名不能为空");
		rtx.append("\r\n\t 2:编码不能为空也不能有重复");
		RichTextString reamrk = new HSSFRichTextString(rtx.toString());
		cell.setCellValue(reamrk);
		sheet.addMergedRegion(new CellRangeAddress(10,10,0,1));
		File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/exportTemp.xls");
        workbook.write(fos);
        fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
//			exportTemp();
//			ImportParams params = new ImportParams();
//			params.setTitleRows(2);
//			params.setHeadRows(3);
//			params.setNeedVerfiy(false);
//			MyExcelVerifyHandler vh = new MyExcelVerifyHandler(false);
//			params.setVerifyHanlder(vh);
//			File file = new File("d:/excel/temp.xls");
//			ExcelImportServer server = new ExcelImportServer();
//			ExcelImportResult ir = server.importExcelByIs(new FileInputStream(
//					file), Map.class, params);
//			System.out.println("getFailList = " + ir.getFailList().size());
//			System.out.println("getList = " + ir.getList().size());
//			System.out.println("isVerfiyFail = " + ir.isVerfiyFail());
//			System.out.println("getMap = " + ir.getMap());
//
//			System.out.println(vh.getMesg());
//			
//			
//			List<Map> list = ir.getList();
//			for(Map item:list){
//				System.out.println(item.toString());
//			}
			String key = "姓名_name";
			
			// System.out.println(list.toArray().toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}
