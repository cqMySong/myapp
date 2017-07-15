package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum BaseMethodEnum implements MyEnum<BaseMethodEnum>{
	BILL("BILL","常规"),ADDNEW("addNew","新增"),EDIT("edit","修改"),REMOVE("remove","删除"),VIEW("view","查看")
	,SAVE("save","保存"),SUBMIT("submit","提交"),AUDIT("audit","审核"),UNAUDIT("unAudit","反审核");
	private String name;
	private String value;
	
	private static final Map<String, BaseMethodEnum> map = new HashMap<String, BaseMethodEnum>();
	static {
        map.put(BILL.getValue(), BILL);
        map.put(ADDNEW.getValue(), ADDNEW);
        map.put(EDIT.getValue(), EDIT);
        map.put(REMOVE.getValue(), REMOVE);
        map.put(VIEW.getValue(), VIEW);
        map.put(SAVE.getValue(), SAVE);
        map.put(SUBMIT.getValue(), SUBMIT);
        map.put(AUDIT.getValue(), AUDIT);
        map.put(UNAUDIT.getValue(), UNAUDIT);
    }
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

	public BaseMethodEnum getEnum(String value) {
		return map.get(value);
	}
}
