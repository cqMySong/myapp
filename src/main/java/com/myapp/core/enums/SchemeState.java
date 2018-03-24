package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum SchemeState implements MyEnum<SchemeState> {
	COMPANY("COMPANY","公司内审中"),SUPERVISION("SUPERVISION","监理审批中"),PROPRIETOR("PROPRIETOR","业主审核中"),
	FINISH("FINISH","审批完成");

	private String name;
	private String value;
	private static final Map<String, SchemeState> map = new HashMap<String, SchemeState>();
	static {
        map.put(COMPANY.getValue(), COMPANY);
        map.put(SUPERVISION.getValue(), SUPERVISION);
        map.put(PROPRIETOR.getValue(), PROPRIETOR);
        map.put(FINISH.getValue(), FINISH);
    }

	SchemeState(String value, String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public SchemeState getEnum(String value) {
		return map.get(value);
	}

}
