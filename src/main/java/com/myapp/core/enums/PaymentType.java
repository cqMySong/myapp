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
public enum PaymentType implements MyEnum<PaymentType> {
	INTERIM("INTERIM","进度款"),
	DOWN_PAYMENT("DOWN_PAYMENT","首付款"),
	SETTLEMENT("SETTLEMENT","结算款");

	private String name;
	private String value;

	private static final Map<String, PaymentType> map = new HashMap<String, PaymentType>();
	static {
        map.put(INTERIM.getValue(), INTERIM);
        map.put(DOWN_PAYMENT.getValue(), DOWN_PAYMENT);
        map.put(SETTLEMENT.getValue(), SETTLEMENT);
    }

	PaymentType(String value, String name){
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
	public PaymentType getEnum(String value) {
		return map.get(value);
	}

}
