package com.myapp.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

public enum UnitType implements MyEnum<UnitType> {
	JSDW("JSDW","建设单位"),SGDW("SGDW","施工单位"),JLDW("JLDW","监理单位"),SJDW("SJDW","设计单位"),KCDW("KCDW","勘测单位");
	
	private String name;
	private String value;

	private static final Map<String, UnitType> map = new HashMap<String, UnitType>();
	static {
        map.put(JSDW.getValue(), JSDW);
        map.put(SGDW.getValue(), SGDW);
        map.put(JLDW.getValue(), JLDW);
        map.put(SJDW.getValue(), SJDW);
        map.put(KCDW.getValue(), KCDW);
    }

	UnitType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public UnitType getEnum(String value) {
		return map.get(value);
	}

}
