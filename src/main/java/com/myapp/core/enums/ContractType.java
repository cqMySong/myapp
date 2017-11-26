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
	PROPRIETOR("PROPRIETOR","业主合同"),
	SUBCONTRACT("SUBCONTRACT","分包合同");

	private String name;
	private String value;

	public static final Map<String, ContractType> map = new HashMap<String, ContractType>();
	static {
        map.put(PROPRIETOR.getValue(), PROPRIETOR);
        map.put(SUBCONTRACT.getValue(), SUBCONTRACT);
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
