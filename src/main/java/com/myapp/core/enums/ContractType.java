package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path:com.myapp.core.enums
 * @description:合同类型
 * @author :ly
 * @date : 2017-10-18
 */
public enum ContractType implements MyEnum<ContractType> {
	MATERIAL("MATERIAL","材料费"),
	ARTIFICIAL("ARTIFICIAL","人工费"),
	EQUIPMENT("EQUIPMENT","机械费"),
	OTHER("OTHER","其他");

	private String name;
	private String value;

	public static final Map<String, ContractType> map = new HashMap<String, ContractType>();
	static {
        map.put(MATERIAL.getValue(), MATERIAL);
        map.put(ARTIFICIAL.getValue(), ARTIFICIAL);
        map.put(EQUIPMENT.getValue(), EQUIPMENT);
		map.put(OTHER.getValue(), OTHER);
    }

	ContractType(String value, String name){
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
	public ContractType getEnum(String value) {
		return map.get(value);
	}

}
