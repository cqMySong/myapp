package com.myapp.core.exception.db;

import cn.afterturn.easypoi.exception.excel.ExcelExportException;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月14日 
 * @system:
 * 暂时还未用上
 *-----------MySong---------------
 */
public class ResultException extends Exception {
	private String mesg="";
	public ResultException(){
		
	}
	
	public ResultException(String mesg){
		this.mesg = mesg;
	}
	
	public String getMessage() {
		return mesg;
	}
}
