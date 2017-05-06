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
	private int stateCode;
	private String mesg;
	private Object data;
	private Object other;
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public String getMesg() {
		return mesg;
	}
	public void setMesg(String mesg) {
		this.mesg = mesg;
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
	
}
