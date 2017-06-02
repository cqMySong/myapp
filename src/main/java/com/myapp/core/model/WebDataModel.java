package com.myapp.core.model;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *  jsp 与 controller 交互的数据模型封装
 *-----------MySong---------------
 */
public class WebDataModel {
	private int statusCode;
	private String statusMesg;
	private Object data;
	private Object other;
	private String operate;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusMesg() {
		return statusMesg;
	}
	public void setStatusMesg(String statusMesg) {
		this.statusMesg = statusMesg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getOther() {
		return other;
	}
	public void setOther(Object other) {
		this.other = other;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}
