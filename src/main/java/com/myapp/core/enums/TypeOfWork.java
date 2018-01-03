package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 工种
 * @author :ly
 * @date:2017-10-18
 */
public enum TypeOfWork implements MyEnum<TypeOfWork> {
	GENERAL("GENERAL","普工"),
	MECHANIC("MECHANIC","技工");

	private String name;
	private String value;

	public static final Map<String, TypeOfWork> map = new HashMap<String, TypeOfWork>();
	static {
        map.put(GENERAL.getValue(), GENERAL);
		map.put(MECHANIC.getValue(), MECHANIC);
    }

	TypeOfWork(String value, String name){
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
	public TypeOfWork getEnum(String value) {
		return map.get(value);
	}

}
