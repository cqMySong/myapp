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
public enum OrgTypeEnum implements MyEnum<OrgTypeEnum>{
	COMPANYORG("COMPANYORG","公司"),ADMINORG("ADMINORG","部门"),PROJECTORG("PROJECTORG","项目部");
	private String name;
	private String value;
	
	private static final Map<String, OrgTypeEnum> map = new HashMap<String, OrgTypeEnum>();
	static {
        map.put(COMPANYORG.getValue(), COMPANYORG);
        map.put(ADMINORG.getValue(), ADMINORG);
        map.put(PROJECTORG.getValue(), PROJECTORG);
    }
	OrgTypeEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public OrgTypeEnum getEnum(String value) {
		return map.get(value);
	}
}
