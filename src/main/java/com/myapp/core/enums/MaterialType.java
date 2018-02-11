package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 物料分类
 * @author :ly
 * @date:2017-10-18
 */
public enum MaterialType implements MyEnum<MaterialType> {
	STRUCTURE("STRUCTURE","材料"),
	APPARATUS("APPARATUS","机械");

	private String name;
	private String value;

	public static final Map<String, MaterialType> map = new HashMap<String, MaterialType>();
	static {
        map.put(STRUCTURE.getValue(), STRUCTURE);
		map.put(APPARATUS.getValue(), APPARATUS);
    }

	MaterialType(String value, String name){
		this.name = name;
		this.value = value;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getValue() {
		return this.value;
	}
	@Override
	public MaterialType getEnum(String value) {
		return map.get(value);
	}

}
