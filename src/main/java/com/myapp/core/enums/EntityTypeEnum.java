package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 * DISABLE 禁止登录等其他操作，FREEZE 只是禁止一些操作
 *-----------MySong---------------
 */
public enum EntityTypeEnum implements MyEnum<EntityTypeEnum> {
	BASEDATA("BASEDATA","基础数据"),BIZBILL("BIZBILL","业务数据"),ENTRY("ENTRY","分录"),OTHER("OTHER","其他");

	private String name;
	private String value;
	private static final Map<String, EntityTypeEnum> map = new HashMap<String, EntityTypeEnum>();
	static {
        map.put(BASEDATA.getValue(), BASEDATA);
        map.put(BIZBILL.getValue(), BIZBILL);
        map.put(ENTRY.getValue(), ENTRY);
        map.put(OTHER.getValue(), OTHER);
    }

	EntityTypeEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public EntityTypeEnum getEnum(String value) {
		return map.get(value);
	}

}
