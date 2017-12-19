package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path : com.myapp.enums
 * @description : 合同签订方式
 * @author :ly
 * @date : 2017-10-18
 */
public enum ContractSignMethod implements MyEnum<ContractSignMethod> {
	/**
	 * 合同签订方式
	 */
	CURRENT("CURRENT","现有合同条款"),
	NEGOTIATION("NEGOTIATION","定向谈判"),
	BIDDING("BIDDING","招投标"),
	OTHER("OTHER","其它");

	private String name;
	private String value;

	private static final Map<String, ContractSignMethod> map = new HashMap<String, ContractSignMethod>();
	static {
        map.put(CURRENT.getValue(), CURRENT);
        map.put(NEGOTIATION.getValue(), NEGOTIATION);
        map.put(BIDDING.getValue(), BIDDING);
		map.put(OTHER.getValue(), OTHER);
    }

	ContractSignMethod(String value, String name){
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
	public ContractSignMethod getEnum(String value) {
		return map.get(value);
	}

}
