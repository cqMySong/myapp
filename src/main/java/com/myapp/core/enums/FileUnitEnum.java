package com.myapp.core.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *-----------MySong---------------
 */
public enum FileUnitEnum implements MyEnum<FileUnitEnum,String> {
	B("B","B"),KB("KB","KB"),MB("MB","MB"),GB("GB","GB");

	private String name;
	private String value;
	private static final Map<String, FileUnitEnum> map = new HashMap<String, FileUnitEnum>();
	static {
        map.put(B.getValue(), B);
        map.put(KB.getValue(), KB);
        map.put(MB.getValue(), MB);
        map.put(GB.getValue(), GB);
    }

	FileUnitEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public FileUnitEnum getEnum(String value) {
		return map.get(value);
	}

}
