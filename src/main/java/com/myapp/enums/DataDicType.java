package com.myapp.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 */
public enum DataDicType implements MyEnum<DataDicType> {
	STRUCTURAL_MATERIAL("STRUCTURAL_MATERIAL","结构材料"),
	DECORATIVE_MATERIAL("DECORATIVE_MATERIAL","装饰材料"),
	SPECIAL_MATERIAL("SPECIAL_MATERIAL","专用材料");

	private String name;
	private String value;

	private static final Map<String, DataDicType> map = new HashMap<String, DataDicType>();
	static {
        map.put(STRUCTURAL_MATERIAL.getValue(), STRUCTURAL_MATERIAL);
        map.put(DECORATIVE_MATERIAL.getValue(), DECORATIVE_MATERIAL);
        map.put(SPECIAL_MATERIAL.getValue(), SPECIAL_MATERIAL);
    }

	DataDicType(String value, String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public DataDicType getEnum(String value) {
		return map.get(value);
	}

}
