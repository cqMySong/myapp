package com.myapp.core.util;

import java.util.Map;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月14日 
 * @system:
 * easypoi  excel导入时候的行数据验证接口
 *-----------MySong---------------
 */
public class MyExcelVerifyHandler implements IExcelVerifyHandler<Map> {
	
	private boolean isAbort = false;//验证到错误是否终止数据的解析
	private int maxErrors = 50;//当遇到最大错误数将强制返回数据解析
	private int rowIdx = 0;
	
	public ExcelVerifyHanlderResult verifyHandler(Map obj) {
		rowIdx++;
		
		return null;
	}

}
