package com.myapp.core.enums;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum BaseMethodEnum {
	BILL("BILL","常规"),ADDNEW("addNew","新增"),EDIT("edit","修改"),REMOVE("remove","删除"),VIEW("view","查看")
	,SAVE("save","保存"),SUBMIT("submit","提交"),AUDIT("audit","审核"),UNAUDIT("unAudit","反审核");
	private String name;
	private String value;

	BaseMethodEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}
}
