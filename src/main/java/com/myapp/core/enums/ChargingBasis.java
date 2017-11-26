package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path:com.myapp.core.enums
 * @description:计费依据
 * @author :ly
 * @date : 2017-11-25
 */
public enum ChargingBasis implements MyEnum<ChargingBasis> {
	INCREASE_CONTRACT("INCREASE_CONTRACT","合同外增加"),
	EMERGENCY_INSPECTION("EMERGENCY_INSPECTION ","应急检查"),
	OWNER_ORDER("OWNER_ORDER","业主指令"),
	CONTRACT_STIPULATION("CONTRACT_STIPULATION","合同约定"),
	INCREASE_QUANTITY("INCREASE_QUANTITY","工程量增加"),
	OTHER("OTHER","其他");

	private String name;
	private String value;

	public static final Map<String, ChargingBasis> map = new HashMap<String, ChargingBasis>();
	static {
        map.put(INCREASE_CONTRACT.getValue(), INCREASE_CONTRACT);
		map.put(EMERGENCY_INSPECTION.getValue(), EMERGENCY_INSPECTION);
        map.put(OWNER_ORDER.getValue(), OWNER_ORDER);
		map.put(CONTRACT_STIPULATION.getValue(), CONTRACT_STIPULATION);
		map.put(INCREASE_QUANTITY.getValue(), INCREASE_QUANTITY);
		map.put(OTHER.getValue(), OTHER);
    }

	ChargingBasis(String value, String name){
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
	public ChargingBasis getEnum(String value) {
		return map.get(value);
	}

}
