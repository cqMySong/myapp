package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path:com.myapp.core.enums
 * @description:付款方式
 * @author :ly
 * @date : 2017-10-30
 */
public enum PaymentMethod implements MyEnum<PaymentMethod> {
	ADVANCE("ADVANCE","预付款"),
	AFTER_GOODS("AFTER_GOODS","先款后货"),
	MONTHLY("MONTHLY","按月部分结算"),
	NODE("NODE","节点付款"),
	COMPLETION("COMPLETION","完工付款");

	private String name;
	private String value;

	private static final Map<String, PaymentMethod> map = new HashMap<String, PaymentMethod>();
	static {
        map.put(ADVANCE.getValue(), ADVANCE);
        map.put(AFTER_GOODS.getValue(), AFTER_GOODS);
        map.put(MONTHLY.getValue(), MONTHLY);
		map.put(NODE.getValue(), NODE);
		map.put(COMPLETION.getValue(),COMPLETION);
    }

	PaymentMethod(String value, String name){
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
	public PaymentMethod getEnum(String value) {
		return map.get(value);
	}

}
