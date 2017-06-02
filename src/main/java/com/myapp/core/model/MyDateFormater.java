package com.myapp.core.model;

import java.util.Date;

import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
public class MyDateFormater implements DataFormater<Date> {
	
	private Date date;
	private String formatString;
	
	MyDateFormater(Date date){
		this.date = date;
		this.formatString = DateUtil.DATEFORMT_YMD;
	}
	MyDateFormater(Date date,String formatString){
		this.date = date;
		this.formatString = formatString;
	}
	
	public Date getData() {
		return this.date;
	}

	public String getValue() {
		return DateUtil.formatDateByFormat(this.date, this.formatString);
	}

}
