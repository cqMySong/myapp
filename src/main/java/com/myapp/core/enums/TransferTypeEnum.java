package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 移交类型
 * @author :ly
 * @date:2017-10-18
 */
public enum TransferTypeEnum implements MyEnum<TransferTypeEnum> {
	UNIT("UNIT","单位"),
	INTERNAL_ORG("INTERNAL_ORG ","内部组织");

	private String name;
	private String value;

	public static final Map<String, TransferTypeEnum> map = new HashMap<String, TransferTypeEnum>();
	static {
        map.put(UNIT.getValue(), UNIT);
		map.put(INTERNAL_ORG.getValue(), INTERNAL_ORG);
    }

	TransferTypeEnum(String value, String name){
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
	public TransferTypeEnum getEnum(String value) {
		return map.get(value);
	}

}
