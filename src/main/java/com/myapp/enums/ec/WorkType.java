package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

public enum WorkType implements MyEnum<WorkType> {

	SCHEUAL("SCHEUAL", "进度"), QUALITY("QUALITY", "质量"), SAFTY("SAFTY", "安全"),COST("COST","成本"),TECH("TECH","技术"),MATERIAL("MATERIAL","材料"),INFOM("INFOM","资料"),OTHER("OTHER","其他");

	private String name;
	private String value;
	private static final Map<String, WorkType> map = new HashMap<String, WorkType>();

	static {
		map.put(SCHEUAL.getValue(), SCHEUAL);
		map.put(QUALITY.getValue(), QUALITY);
		map.put(SAFTY.getValue(), SAFTY);
		map.put(COST.getValue(), COST);
		map.put(TECH.getValue(), TECH);
		map.put(MATERIAL.getValue(), MATERIAL);
		map.put(INFOM.getValue(), INFOM);
		map.put(OTHER.getValue(), OTHER);
	}

	WorkType(String value, String name) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public WorkType getEnum(String value) {
		return map.get(value);
	}
}
