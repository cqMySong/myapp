package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path:com.myapp.core.enums
 * @description:付款类型
 * @author :ly
 * @date : 2017-10-30
 */
public enum SettleType implements MyEnum<SettleType> {
	INTERIM("INTERIM","进度"),
	SETTLEMENT("SETTLEMENT","结算");

	private String name;
	private String value;

	private static final Map<String, SettleType> map = new HashMap<String, SettleType>();
	static {
        map.put(INTERIM.getValue(), INTERIM);
        map.put(SETTLEMENT.getValue(), SETTLEMENT);
    }

	SettleType(String value, String name){
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
	public SettleType getEnum(String value) {
		return map.get(value);
	}

}
