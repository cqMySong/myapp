package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月13日 
 * @system:
 *  icon 图标库类型
 *-----------MySong---------------
 */
public enum IconType implements MyEnum<IconType> {
	fa("fa","fontawesome"),glyphicon("glyphicon","glyphicon"),OTHER("other","其他");

	private String name;
	private String value;
	private static final Map<String, IconType> map = new HashMap<String, IconType>();
	static {
		map.put(fa.getValue(), fa);
        map.put(glyphicon.getValue(), glyphicon);
        map.put(OTHER.getValue(), OTHER);
    }

	IconType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public IconType getEnum(String value) {
		return map.get(value);
	}

}
