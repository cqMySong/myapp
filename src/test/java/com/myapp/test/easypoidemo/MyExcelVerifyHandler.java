package com.myapp.test.easypoidemo;

import java.util.Map;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月13日 
 * @system:
 *
 *-----------MySong---------------
 */
public class MyExcelVerifyHandler implements IExcelVerifyHandler<Map<String,Object>> {

	int rowIdx = 0;
	String msg = "";
	boolean toAbort = false;
	public MyExcelVerifyHandler(boolean abort){
		this.toAbort = abort;
	}
	
	public ExcelVerifyHanlderResult verifyHandler(Map<String,Object> obj) {
		rowIdx++;
		obj.put("rowIdx", rowIdx);
//		obj.put("姓名_name", "ddddddddd");
		String _msg = "";
		boolean succes = true;
		if(!succes){
			_msg = "ssss";
			this.msg +=";"+_msg;
			if(toAbort){
				throw new ExcelExportException("第["+rowIdx+"]行数据 发生了数据错误!");
			}
		}
		
		return new ExcelVerifyHanlderResult(succes,msg);
	}
	
	public String getMesg(){
		return this.msg;
	}

}
