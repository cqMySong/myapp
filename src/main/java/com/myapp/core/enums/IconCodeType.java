package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月13日 
 * @system:
 *  icon 编码方式
 *-----------MySong---------------
 */
public enum IconCodeType implements MyEnum<IconCodeType> {
	UNICODE("UNICODE","Unicode编码"),CLASS("CLASS","Class编码"),IMAGE("IMAGE","图片");

	private String name;
	private String value;
	private static final Map<String, IconCodeType> map = new HashMap<String, IconCodeType>();
	static {
		map.put(UNICODE.getValue(), UNICODE);
        map.put(CLASS.getValue(), CLASS);
        map.put(IMAGE.getValue(), IMAGE);
    }

	IconCodeType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public IconCodeType getEnum(String value) {
		return map.get(value);
	}

}
