package com.myapp.test.easypoidemo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月7日 
 * @system:
 *
 *-----------MySong---------------
 */
public class ExcelExportTest {

	public static void main(String[] args) {
		try{
			
			List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		    entity.add(new ExcelExportEntity("姓名", "01"));
		    ExcelExportEntity sex = new ExcelExportEntity("性别", "02");
		    sex.setReplace(new String[]{"男_0","女_1"});
		    entity.add(sex);
		    ExcelExportEntity et = new ExcelExportEntity("实际年龄","03");
		    et.setGroupName("年龄");
		    et.setStatistics(true);
//		    et.setOrderNum(1);
		    entity.add(et);
		    ExcelExportEntity et2 = new ExcelExportEntity("虚拟年龄","04");
		    et2.setGroupName("年龄");
		    et2.setFormat("yyyy-MM-dd");
		    et2.setWidth(50);
//		    et2.setOrderNum(2);
		    entity.add(et2);
		    ExcelExportEntity remark = new ExcelExportEntity("备注", "05");
		    remark.setWrap(true);
		    remark.setOrderNum(4);
		    entity.add(remark);
		    
		    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		    Map<String, Object> map;
		    for (int i = 0; i < 10; i++) {
		        map = new HashMap<String, Object>();
		        map.put("01", "1" + i);
		        map.put("02", i%2);
		        map.put("03", "5" + i);
		        map.put("04", new Date());
		        map.put("05","1:sdfaf;\n2:adfasdfaadfa大发送到");
		        list.add(map);
		    }
		    Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"xxx人 2018-01-05-7 制表", "sheet"), entity, list); 
			File savefile = new File("D:/excel/");
	        if (!savefile.exists()) {
	            savefile.mkdirs();
	        }
	        FileOutputStream fos = new FileOutputStream("D:/excel/exportTemp_image.xls");
	        workbook.write(fos);
	        fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

}
